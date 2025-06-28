# Tổng hợp kiến thức về tối ưu hóa lưu trữ file trong quá trình tạo sản phẩm

## Vấn đề
Khi người dùng tải ảnh lên trong quá trình tạo sản phẩm nhưng sau đó hủy bỏ quá trình tạo, các file vẫn được lưu trữ trong hệ thống, gây lãng phí tài nguyên.

## Giải pháp tối ưu

### 1. Quản lý trạng thái file
- **Thêm trường `usageStatus` vào entity File**:
  ```java
  public enum FileUsageStatus {
      TEMPORARY, // File mới tải lên, chưa được liên kết
      USED,      // File đã được liên kết với sản phẩm
      UNUSED     // File không còn được sử dụng
  }
  ```
- **Đánh dấu file là `TEMPORARY` khi tải lên**
- **Cập nhật thành `USED` khi sản phẩm được tạo thành công**

### 2. Cơ chế xóa mềm (Soft Delete)
- **Lợi ích của xóa mềm**:
  - Khả năng khôi phục dữ liệu khi cần thiết
  - Duy trì tính nhất quán và toàn vẹn dữ liệu
  - Tránh mất tham chiếu giữa sản phẩm và file
  - Duy trì lịch sử hoạt động đầy đủ

- **Triển khai xóa mềm**:
  ```java
  public void deleteFile(UUID fileId) {
      File file = fileDomainRepository.getById(fileId);
      file.setDeleted(true);
      // Ghi lại lịch sử
      fileDomainRepository.save(file);
  }
  ```

### 3. Dọn dẹp tự động
- **Tác vụ định kỳ để xóa file không cần thiết**:
  ```java
  @Scheduled(cron = "0 0 2 * * ?") // Chạy vào 2 giờ sáng mỗi ngày
  public void cleanupTemporaryFiles() {
      // Tìm các file có trạng thái TEMPORARY và đã tạo cách đây ít nhất 24 giờ
      LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);
      List<File> unusedFiles = fileDomainRepository.findTemporaryFilesCreatedBefore(cutoffTime);

      for (File file : unusedFiles) {
          // Xóa mềm file
          fileCommandService.deleteFile(file.getId());
      }
  }
  ```

### 4. Quản lý thời gian sống của file
- **Thêm trường thời gian**:
  ```java
  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime expiresAt = LocalDateTime.now().plusHours(24);
  ```

### 5. API để quản lý file
- **API hủy tải lên**:
  ```java
  @PostMapping("/file/cancel-upload")
  public ApiResponses<Void> cancelFileUpload(@RequestBody List<UUID> fileIds) {
      for (UUID fileId : fileIds) {
          fileCommandService.deleteFile(fileId);
      }
      return ApiResponses.<Void>builder()
              .success(true)
              .code(204)
              .message("Files marked as deleted successfully")
              .build();
  }
  ```

- **API xác nhận sử dụng file**:
  ```java
  @PutMapping("/file/confirm-usage/{fileId}")
  public ApiResponses<FileResponse> confirmFileUsage(@PathVariable UUID fileId) {
      // Cập nhật trạng thái file thành USED
  }
  ```

### 6. Cải tiến bảo mật và kiểm soát truy cập
- **Kiểm tra quyền sở hữu file**:
  ```java
  @PreAuthorize("hasRole('USER') and @fileSecurityService.isFileOwner(authentication, #fileId)")
  @DeleteMapping("file/delete/{fileId}")
  public ApiResponses<Void> deleteFile(@PathVariable UUID fileId) {
      // Xóa file
  }
  ```

- **Thêm trạng thái "đang sử dụng" cho file**:
  ```java
  public enum FileStatus {
      AVAILABLE,    // File có thể được sử dụng
      IN_USE,       // File đang được sử dụng trong quá trình tạo sản phẩm
      DELETED       // File đã bị xóa mềm
  }
  ```

### 7. Cải tiến nâng cao
- **Lưu trữ tạm thời trên client** trước khi gửi lên server
- **Hệ thống lưu trữ phân tầng** cho các file tạm thời và dài hạn
- **Xử lý batch** để tối ưu hiệu suất khi xóa nhiều file
- **Cơ chế thông báo** khi sản phẩm bị hủy để kích hoạt xóa file
- **Phân tích sử dụng** để điều chỉnh thời gian hết hạn dựa trên dữ liệu thực tế
- **Nén file và deduplication** để giảm không gian lưu trữ

## Cân nhắc giữa xóa mềm và xóa vĩnh viễn
Mặc dù xóa mềm tốn nhiều bộ nhớ hơn, nhưng lợi ích về khả năng khôi phục, tính nhất quán dữ liệu và hiệu suất hệ thống vượt trội hơn. Để giảm thiểu vấn đề bộ nhớ, có thể triển khai tác vụ định kỳ để xóa vĩnh viễn các file đã bị đánh dấu xóa mềm sau một khoảng thời gian nhất định.