package hello.hellospring.Batch.Controller;

import hello.hellospring.Batch.Model.PostCount;
import hello.hellospring.Batch.Model.PostCountDTO;
import hello.hellospring.Batch.Service.PostCountService;
import hello.hellospring.Post.Model.PostDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class BatchController {

    public final PostCountService postCountService;

    /**
     * 한달 batch 결과 가져오기
     * @return
     */
    @GetMapping("/chart/month/{month}")
    public ResponseEntity<Map<String, Object>> recent(@PathVariable int month) {
        List<String> labelList = postCountService.getLabels(month);
        List<Integer> exerciseList = postCountService.getPostCountByMonth(month, "운동");
        List<Integer> cultureList = postCountService.getPostCountByMonth(month, "문화/예술");
        List<Integer> cookList = postCountService.getPostCountByMonth(month, "요리");
        List<Integer> studyList = postCountService.getPostCountByMonth(month, "스터디");
        List<Integer> artList = postCountService.getPostCountByMonth(month, "공예");
        List<Integer> restList = postCountService.getPostCountByMonth(month, "기타");

        HashMap<String, Object> result = new HashMap<>();
        result.put("labelList", labelList);
        result.put("exerciseList", exerciseList);
        result.put("cultureList", cultureList);
        result.put("cookList", cookList);
        result.put("studyList", studyList);
        result.put("artList", artList);
        result.put("restList", restList);

        return ResponseEntity.ok().body(result);
    }
}
