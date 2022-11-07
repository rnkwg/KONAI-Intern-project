package hello.hellospring.Batch.Service;

import hello.hellospring.Batch.Model.PostCount;
import hello.hellospring.Batch.Repository.PostCountRepository;
import hello.hellospring.Batch.Repository.PostCountRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostCountService {

    public final PostCountRepository postCountRepository;

    public final PostCountRepositoryImpl postCountRepositoryImpl;

    /**
     * month 의 postCount 목록 가져오기
     * @return
     */
    public List<Integer> getPostCountByMonth(int month, String categoryName) {
        int lastDate = getLastDate(month);
        LocalDateTime startDate = LocalDateTime.of(2022, month, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2022, month, lastDate, 0, 0, 0);

        List<PostCount> postCountList = new ArrayList<>();
        Integer[] countArray = new Integer[lastDate];
        for(int i = 0; i < lastDate; i++)
            countArray[i] = 0;

        postCountList = postCountRepositoryImpl.getPostCountByMonth(startDate, endDate, categoryName);

        for(int i = 0; i < postCountList.size(); i++){
            String data = String.valueOf(postCountList.get(i));
            String[] date = data.split("T");
            String[] day = date[0].split("-");
            int dayNum = Integer.parseInt(day[2]);

            countArray[dayNum - 1] = Math.toIntExact(postCountList.get(i).getCount());
        }
        return List.of(countArray);
    }

    /**
     * label List 로 만들어서 반환하기
     * @param month
     * @return
     */
    public List<String> getLabels(int month) {
        int endDay = getLastDate(month);
        List<String> labelList = new ArrayList<>();
        for(int i = 1; i <= endDay; i++) {
            labelList.add("2022-" + String.valueOf(month) + "-" + String.valueOf(i));
        }
        return labelList;
    }

    /**
     * month 의 마지막 날짜 가져오기
     * @param month
     * @return
     */
    public int getLastDate(int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(2022, month - 1, 1);
        int endDay = (cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return endDay;
    }
}
