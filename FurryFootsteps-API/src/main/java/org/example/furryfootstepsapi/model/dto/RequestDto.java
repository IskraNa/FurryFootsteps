package org.example.furryfootstepsapi.model.dto;

import org.example.furryfootstepsapi.model.Post;
import org.example.furryfootstepsapi.model.Request;

public class RequestDto {
    private Long postId;
    private Request.RequestStatus status;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Request toRequest(Post post) {
        Request request = new Request();
        request.setPost(post);
        request.setStatus(status);
        return request;
    }

    public Request.RequestStatus getStatus() {
        return status;
    }

    public void setStatus(Request.RequestStatus status) {
        this.status = status;
    }
}
