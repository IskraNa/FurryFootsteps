package org.example.furryfootstepsapi.web;

import org.example.furryfootstepsapi.model.Request;
import org.example.furryfootstepsapi.service.PostService;
import org.example.furryfootstepsapi.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = {"*"})
public class RequestController {

    private final RequestService requestService;
    private final PostService postService;

    public RequestController(RequestService requestService, PostService postService) {
        this.requestService = requestService;
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Request>> getRequests() {
        return ResponseEntity.ok().body(this.requestService.findAll());
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Request> getRequestById(@PathVariable Long id) {
//        return this.requestService.findById(id).map(request -> ResponseEntity.ok().body(request))
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Request>> getRequestsByUserId(@PathVariable Long userId) {
    List<Request> requests = requestService.getRequestsByUserId(userId);
    return ResponseEntity.ok().body(requests);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<Void> acceptRequest (@PathVariable Long requestId)
    {
        requestService.acceptRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{requestId}/decline")
    public void declineRequest(@PathVariable Long requestId) {
        requestService.declineRequest(requestId);
    }
    @PostMapping("/create")
    public ResponseEntity<Request> createRequest(@RequestBody Request request,
                                                 @RequestParam Long postId,
                                                 @RequestParam Long userId){

        System.out.println("Received postId:"+postId);
        System.out.println("Received userId:"+userId);
        Request createdRequest = requestService.create(request, postId, userId);
        return ResponseEntity.created(URI.create("/api/requests/" + createdRequest.getId())).body(createdRequest);
    }


}
