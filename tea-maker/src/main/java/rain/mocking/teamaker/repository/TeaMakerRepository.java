package rain.mocking.teamaker.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import rain.mocking.teamaker.model.TeaMaker;
import rain.mocking.teamaker.repository.mapper.TeaMakerMapper;

@Service
public class TeaMakerRepository extends ServiceImpl<TeaMakerMapper, TeaMaker>
    implements IService<TeaMaker> {}
