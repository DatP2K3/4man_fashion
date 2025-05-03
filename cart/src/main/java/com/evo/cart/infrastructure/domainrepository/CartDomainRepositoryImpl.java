package com.evo.cart.infrastructure.domainrepository;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.evo.cart.domain.Cart;
import com.evo.cart.domain.CartItem;
import com.evo.cart.domain.repository.CartDomainRepository;
import com.evo.cart.infrastructure.adapter.Product.client.ProductClient;
import com.evo.cart.infrastructure.persistence.entity.CartEntity;
import com.evo.cart.infrastructure.persistence.entity.CartItemEntity;
import com.evo.cart.infrastructure.persistence.mapper.CartEntityMapper;
import com.evo.cart.infrastructure.persistence.mapper.CartItemEntityMapper;
import com.evo.cart.infrastructure.persistence.repository.CartEntityRepository;
import com.evo.cart.infrastructure.persistence.repository.CartItemEntityRepository;
import com.evo.cart.infrastructure.support.exception.AppErrorCode;
import com.evo.cart.infrastructure.support.exception.AppException;
import com.evo.common.dto.response.ProductDTO;
import com.evo.common.dto.response.ProductVariantDTO;
import com.evo.common.repository.AbstractDomainRepository;

@Repository
public class CartDomainRepositoryImpl extends AbstractDomainRepository<Cart, CartEntity, UUID>
        implements CartDomainRepository {
    private final CartEntityMapper cartEntityMapper;
    private final CartEntityRepository cartEntityRepository;
    private final CartItemEntityRepository cartItemEntityRepository;
    private final CartItemEntityMapper cartItemEntityMapper;
    private final ProductClient productClient;

    public CartDomainRepositoryImpl(
            CartEntityMapper cartEntityMapper,
            CartEntityRepository cartEntityRepository,
            CartItemEntityRepository cartItemEntityRepository,
            CartItemEntityMapper cartItemEntityMapper,
            ProductClient productClient) {
        super(cartEntityRepository, cartEntityMapper);
        this.cartEntityMapper = cartEntityMapper;
        this.cartEntityRepository = cartEntityRepository;
        this.cartItemEntityRepository = cartItemEntityRepository;
        this.cartItemEntityMapper = cartItemEntityMapper;
        this.productClient = productClient;
    }

    @Override
    public Cart save(Cart cart) {
        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems != null && !cartItems.isEmpty()) {
            List<CartItemEntity> cartItemEntities = this.cartItemEntityMapper.toEntityList(cartItems);
            cartItemEntityRepository.saveAll(cartItemEntities);
        }
        CartEntity cartEntity = this.cartEntityMapper.toEntity(cart);
        cartEntity = this.cartEntityRepository.save(cartEntity);
        return this.enrich(this.cartEntityMapper.toDomainModel(cartEntity));
    }

    @Override
    public List<Cart> getAll() {
        List<CartEntity> cartEntities = this.cartEntityRepository.findAll();
        return this.enrichList(this.cartEntityMapper.toDomainModelList(cartEntities));
    }

    @Override
    public Cart getByUserIdOrNull(UUID userId) {
        CartEntity cartEntity = this.cartEntityRepository.findByUserId(userId).orElse(null);
        return this.enrich(this.cartEntityMapper.toDomainModel(cartEntity));
    }

    @Override
    public Cart getById(UUID uuid) {
        CartEntity cartEntity = this.cartEntityRepository
                .findById(uuid)
                .orElseThrow(() -> new AppException(AppErrorCode.CART_NOT_FOUND));
        return this.enrich(this.cartEntityMapper.toDomainModel(cartEntity));
    }

    @Override
    protected List<Cart> enrichList(List<Cart> carts) {
        if (carts.isEmpty()) return carts;

        List<UUID> cartIds = carts.stream().map(Cart::getId).toList();

        Map<UUID, List<CartItem>> cartItemMap = this.cartItemEntityRepository.findByCartIdIn(cartIds).stream()
                .collect(Collectors.groupingBy(
                        CartItemEntity::getCartId,
                        Collectors.mapping(this.cartItemEntityMapper::toDomainModel, Collectors.toList())));

        carts.forEach(cart -> {
            cart.setCartItems(cartItemMap.get(cart.getId()));
        });

        return this.enrichListCartItemInfo(carts);
    }

    private List<Cart> enrichListCartItemInfo(List<Cart> carts) {
        if (carts.isEmpty()) return carts;
        for (Cart cart : carts) {
            if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) continue;
            for (CartItem cartItem : cart.getCartItems()) {
                Object o = productClient.getProduct(cartItem.getProductId());
                ProductDTO productDTO =
                        productClient.getProduct(cartItem.getProductId()).getData();
                cartItem.setName(productDTO.getName());
                cartItem.setAvatarId(productDTO.getAvatarId());
                cartItem.setDiscountPercent(productDTO.getDiscountPercent());
                cartItem.setDiscountPrice(productDTO.getDiscountPrice());
                cartItem.setDiscountType(productDTO.getDiscountType());
                cartItem.setOriginPrice(productDTO.getOriginPrice());

                List<ProductVariantDTO> productVariantDTOs = productDTO.getProductVariants();
                for (ProductVariantDTO productVariantDTO : productVariantDTOs) {
                    if (productVariantDTO.getId().equals(cartItem.getProductVariantId())) {
                        cartItem.setSize(productVariantDTO.getSize());
                        cartItem.setColor(productVariantDTO.getColor());
                    }
                }
            }
        }
        return carts;
    }
}
