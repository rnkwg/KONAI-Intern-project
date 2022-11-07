package hello.hellospring.Post.Service;

import hello.hellospring.Post.Model.*;
import hello.hellospring.Post.Repository.FileRepository;
import hello.hellospring.Post.Repository.FileRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
    private final FileRepository fileRepository;
    private final PostService postService;
    private final FileRepositoryImpl fileRepositoryImpl;

//    파일 저장하기
    @Transactional
    public void saveFile(MultipartFile fileDTO, Post post) {
        try {
            File file = settingFile(fileDTO, post);
            fileRepository.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    파일 가져오기
    @Transactional
    public FileDTO getFile(Long id) {
        File file = fileRepository.findById(id).get();
        FileDTO fileDTO = new FileDTO(file);
        return fileDTO;
    }

    /**
     * 파일 수정하기
     * @param postId
     * @param fileDTO
     * @return
     */
    @Transactional
    public void updateFile(MultipartFile fileDTO, long postId) {
        Post post = postService.getPost(postId);

//        파일이 없다 : 기존에 파일이 있었다면 삭제 / 기존에 파일이 없었다면 그대로
        if(fileDTO == null) {
            if(post.getFile() != null) {
                fileRepository.deleteById(post.getFile().getFileId());
            } else {

            }

        }

//        파일이 있다 : 그 기존의 파일과 동일한지 검사를 어떻게 해주어야 할까
//        기존의 파일과 동일하면 그대로 / 기존의 파일과 다르면 기존의 파일 삭제 후 새로운 파일 넣어주기
//        그냥 검사하지 말고 바로 업데이트 날려버려
//        만약 글 쓸때는 파일이 없었는데 글 수정하면서 파일을 추가했다면??? 이것도 나누기 필요하겠네
//        1. 기존에 작성한 글에 첨부파일이 있었는데 파일을 바꾸었다 -> update
//        2. 기존에 작성한 글에 첨부파일이 없었는데 파일을 넣어주었다 -> save
        else {
            if(post.getFile() != null) {
                try {
                    File file = settingFile(fileDTO, post);
                    fileRepositoryImpl.updateFile(postId, file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                saveFile(fileDTO, post);
            }
        }
    }

    public File settingFile(MultipartFile fileDTO, Post post) throws IOException {
        String origFilename = fileDTO.getOriginalFilename();
        ImageNameModel imageNameModel = new ImageNameModel();
        String filename = imageNameModel.GenSaveFileName(origFilename);

//        실행되는 위치의 'files' 폴더에 파일이 저장됩니다.
        String savePath = System.getProperty("user.dir") + "\\files";

        String filePath = savePath + "\\" + filename;
        fileDTO.transferTo(new java.io.File(filePath));

        File file = new File();
        file.setPost(post);
        file.setOriginName(origFilename);
        file.setName(filename);
        file.setPath(filePath);

        return file;
    }
}
