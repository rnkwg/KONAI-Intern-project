package hello.hellospring.Post.Model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FileDTO {
    private long fileId;

    private Post post;

    private long postId;

    private String name;

    private String originName;

    private String path;

//    DTO -> Entity
    public File toEntity() {
        return File.builder()
                .fileId(fileId)
                .post(post)
                .name(name)
                .originName(originName)
                .path(path)
                .build();
    }

//    Entity -> DTO
    public FileDTO(File file) {
        fileId = file.getFileId();
        post = file.getPost();
        postId = file.getPost().getPostId();
        name = file.getName();
        originName = file.getOriginName();
        path = file.getPath();
    }
}
