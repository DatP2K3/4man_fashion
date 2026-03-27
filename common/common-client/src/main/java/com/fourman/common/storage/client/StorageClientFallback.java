package com.fourman.common.storage.client;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fourman.common.dto.request.SearchFileRequest;
import com.fourman.common.dto.request.UpdateFileRequest;
import com.fourman.common.dto.response.FileResponse;
import com.fourman.common.dto.response.PagingResponse;
import com.fourman.common.dto.response.Response;
import com.fourman.common.enums.ServiceUnavailableError;
import com.fourman.common.exception.ForwardInnerAlertException;
import com.fourman.common.exception.ResponseException;

import lombok.extern.slf4j.Slf4j;

@Component
public class StorageClientFallback
        implements FallbackFactory<
                StorageClient> { // FallbackFactory: Dùng để xử lý khi gặp lỗi khi gọi api từ Iam Client
    @Override
    public StorageClient create(Throwable cause) {
        return new FallbackWithFactory(cause);
    }

    @Slf4j
    static class FallbackWithFactory implements StorageClient {
        private final Throwable cause;

        FallbackWithFactory(Throwable cause) {
            this.cause = cause;
        }

        @Override
        public Response<List<FileResponse>> uploadFiles(
                List<MultipartFile> files, boolean isPublic, String description) {
            if (cause instanceof ForwardInnerAlertException) {
                return Response.fail((RuntimeException) cause);
            }
            return Response.fail(new ResponseException(ServiceUnavailableError.STORAGE_SERVICE_UNAVAILABLE_ERROR));
        }

        @Override
        public Response<FileResponse> updateFile(UpdateFileRequest updateFileRequest) {
            if (cause instanceof ForwardInnerAlertException) {
                return Response.fail((RuntimeException) cause);
            }
            return Response.fail(new ResponseException(ServiceUnavailableError.STORAGE_SERVICE_UNAVAILABLE_ERROR));
        }

        @Override
        public Response<Void> deleteFile(UUID fileId) {
            if (cause instanceof ForwardInnerAlertException) {
                return Response.fail((RuntimeException) cause);
            }
            return Response.fail(new ResponseException(ServiceUnavailableError.STORAGE_SERVICE_UNAVAILABLE_ERROR));
        }

        @Override
        public Response<FileResponse> getFile(UUID fileId) {
            if (cause instanceof ForwardInnerAlertException) {
                return Response.fail((RuntimeException) cause);
            }
            return Response.fail(new ResponseException(ServiceUnavailableError.STORAGE_SERVICE_UNAVAILABLE_ERROR));
        }

        @Override
        public PagingResponse<FileResponse> searchFiles(SearchFileRequest searchFileRequest) {
            if (cause instanceof ForwardInnerAlertException) {
                return PagingResponse.failPaging((RuntimeException) cause);
            }
            return PagingResponse.failPaging(
                    new ResponseException(ServiceUnavailableError.STORAGE_SERVICE_UNAVAILABLE_ERROR));
        }

        @Override
        public Response<Void> testRetry() {
            if (cause instanceof ForwardInnerAlertException) {
                return Response.fail((RuntimeException) cause);
            }
            return Response.fail(new ResponseException(ServiceUnavailableError.STORAGE_SERVICE_UNAVAILABLE_ERROR));
        }
    }
}
