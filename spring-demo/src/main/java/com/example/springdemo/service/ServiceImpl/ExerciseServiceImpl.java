package com.example.springdemo.service.ServiceImpl;

import com.example.springdemo.common.Pager;
import com.example.springdemo.entity.Exercise;
import com.example.springdemo.mapper.ExerciseDao;
import com.example.springdemo.service.ExerciseService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("ExerciseService")
public class ExerciseServiceImpl implements ExerciseService {

    @Resource
    ExerciseDao exerciseDao;

    @Resource
    MongoTemplate mongoTemplate;

    public Pager<Exercise> getExerciseByPage(Integer pageNum, Integer pageSize) {
//        使用 PagingAndSortingRepository 的接口，但是灵活性较低
//        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
//        Iterator<Exercise> iterator = exerciseDao.findAll(pageRequest).iterator();
//        List<Exercise> list = new ArrayList<>();
//        while (iterator.hasNext()) {
//            list.add(iterator.next());
//        }
//        Long total = exerciseDao.count();
        Query query = new Query().skip((long) (pageNum - 1) * pageSize).limit(pageSize);
        List<Exercise> list = mongoTemplate.find(query, Exercise.class);
        long total = mongoTemplate.estimatedCount(Exercise.class);
        return new Pager<>(list, total, pageNum, pageSize);
    }

    @Override
    public Exercise getExerciseById(Integer id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, Exercise.class);
    }

    @Override
    public Pager<Exercise> getExerciseByType(String type, Integer pageNum, Integer pageSize) {
        Query query = new Query(Criteria.where("type").regex(".*" + type + ".*"));
        long total = mongoTemplate.count(query, Exercise.class);
        List<Exercise> list = mongoTemplate.find(
                query.skip((long) (pageNum - 1) * pageSize).limit(pageSize), Exercise.class);
        return new Pager<>(list, total, pageNum, pageSize);
    }

    @Override
    public Exercise insertOne(Exercise exercise) {
        return mongoTemplate.insert(exercise);
    }

    @Override
    public void deleteById(String _id) {
        mongoTemplate.remove(new Query(Criteria.where("_id").is(_id)), Exercise.class);
    }

    @Override
    public void updateExercise(Exercise exercise) {
        mongoTemplate.save(exercise);
    }

}
