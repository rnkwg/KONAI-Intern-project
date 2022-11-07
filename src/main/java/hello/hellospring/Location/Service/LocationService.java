package hello.hellospring.Location.Service;

import hello.hellospring.Location.Model.Location;
import hello.hellospring.Location.Model.LocationDTO;
import hello.hellospring.Location.Repository.LocationRepository;
import hello.hellospring.Location.Repository.LocationRepositoryImpl;
import hello.hellospring.Post.Model.PostDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {

    private final LocationRepository locationRepository;

    private final LocationRepositoryImpl locationRepositoryImpl;

    /**
     * 위치 저장
     * @param location
     * @return
     */
    @Transactional
    public long saveLocation(Location location) {
        return locationRepository.save(location).getLocationId();
    }

    /**
     * 위치 변경
     * @param location
     */
    @Transactional
    public void updateLocation(Location location) {
        locationRepositoryImpl.updateLocation(location.getLocationId(), location);
    }

    /**
     * 위치 삭제
     * @param locationId
     */
    @Transactional
    public void deleteLocation(long locationId) {
        locationRepository.deleteById(locationId);
    }

    /**
     * 카테고리에 해당하는 글들의 위치 좌표 가져오기
     * @param postList
     * @return
     */
    public List<LocationDTO> getAllLocationByCategory(List<PostDTO> postList) {
//        List<PostDTO> postList = postService.getAllPostByCategoryId(categoryId);
        List<LocationDTO> locationList = new ArrayList<>();
        for(PostDTO postDTO : postList) {
            locationList.add(new LocationDTO(postDTO.getLocation()));
        }
        return locationList;
    }
}
