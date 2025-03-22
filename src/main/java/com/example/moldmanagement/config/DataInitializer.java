package com.example.moldmanagement.config;

import com.example.moldmanagement.entity.Material;
import com.example.moldmanagement.entity.Mold;
import com.example.moldmanagement.entity.Process;
import com.example.moldmanagement.entity.User;
import com.example.moldmanagement.repository.MaterialRepository;
import com.example.moldmanagement.repository.MoldRepository;
import com.example.moldmanagement.repository.ProcessRepository;
import com.example.moldmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MoldRepository moldRepository;
    private final ProcessRepository processRepository;
    private final MaterialRepository materialRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(MoldRepository moldRepository, 
                          ProcessRepository processRepository,
                          MaterialRepository materialRepository,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.moldRepository = moldRepository;
        this.processRepository = processRepository;
        this.materialRepository = materialRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // 初始化用户数据
        initUserData();
        
        // 检查是否已有模具数据
        if (moldRepository.count() > 0) {
            return; // 如果已有数据，则不初始化模具和辅料
        }

        // 初始化模具数据
        initMoldData();
        
        // 初始化辅料数据
        initMaterialData();
    }
    
    private void initUserData() {
        // 检查是否已有管理员账户
        if (!userRepository.existsByUsername("admin")) {
            // 创建默认管理员账户
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setName("系统管理员");
            admin.setEmail("admin@example.com");
            admin.setRole("ADMIN");
            admin.setActive(true);
            
            userRepository.save(admin);
            System.out.println("默认管理员账户已创建");
        }

        // 检查是否已有测试用户账户
        if (!userRepository.existsByUsername("user")) {
            // 创建默认测试用户
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setName("测试用户");
            user.setEmail("user@example.com");
            user.setRole("USER");
            user.setActive(true);
            
            userRepository.save(user);
            System.out.println("默认测试用户已创建");
        }
    }

    private void initMoldData() {
        // 创建模具1：SC25-01
        Mold mold1 = new Mold();
        mold1.setMoldCode("SC25-01");
        mold1.setOrderTime(LocalDateTime.now().minusDays(10));
        mold1.setPriority(1);
        mold1.setCompletionRate(0.0);
        mold1.setStatus("notStarted");
        
        moldRepository.save(mold1);
     // 为SC25-01添加工序数据 - 添加这段代码
        Process designProcess1 = new Process();
        designProcess1.setName("设计图纸");
        designProcess1.setWeight(2.0);
        designProcess1.setStatus("notStarted");
        designProcess1.setModuleType("frame");
        designProcess1.setMold(mold1);
        
        Process materialProcess1 = new Process();
        materialProcess1.setName("模料");
        materialProcess1.setWeight(7.0);
        materialProcess1.setStatus("notStarted");
        materialProcess1.setModuleType("frame");
        materialProcess1.setMold(mold1);
        
        processRepository.saveAll(Arrays.asList(designProcess1, materialProcess1));

        // 创建模具2：SC25-02（已开始生产）
        Mold mold2 = new Mold();
        mold2.setMoldCode("SC25-02");
        mold2.setOrderTime(LocalDateTime.now().minusDays(15));
        mold2.setStartTime(LocalDateTime.now().minusDays(8));
        mold2.setPriority(2);
        mold2.setCompletionRate(15.0);
        mold2.setStatus("inProgress");
        
        moldRepository.save(mold2);

        // 为模具2添加工序
        Process designProcess = new Process();
        designProcess.setName("设计图纸");
        designProcess.setWeight(2.0);
        designProcess.setStatus("completed");
        designProcess.setStartTime(LocalDateTime.now().minusDays(8));
        designProcess.setEndTime(LocalDateTime.now().minusDays(7));
        designProcess.setModuleType("frame");
        designProcess.setMold(mold2);
        
        Process materialProcess = new Process();
        materialProcess.setName("模料");
        materialProcess.setWeight(7.0);
        materialProcess.setStatus("inProgress");
        materialProcess.setStartTime(LocalDateTime.now().minusDays(6));
        materialProcess.setModuleType("frame");
        materialProcess.setMold(mold2);
        
        Process roughProcess = new Process();
        roughProcess.setName("粗加工");
        roughProcess.setWeight(15.0);
        roughProcess.setStatus("notStarted");
        roughProcess.setModuleType("frame");
        roughProcess.setMold(mold2);
        
        Process coreProcess = new Process();
        coreProcess.setName("模芯");
        coreProcess.setWeight(1.5);
        coreProcess.setStatus("notStarted");
        coreProcess.setModuleType("main_parts");
        coreProcess.setMold(mold2);
        
        Process cavityProcess = new Process();
        cavityProcess.setName("模腔");
        cavityProcess.setWeight(3.0);
        cavityProcess.setStatus("notStarted");
        cavityProcess.setModuleType("main_parts");
        cavityProcess.setMold(mold2);
        
        processRepository.saveAll(Arrays.asList(
            designProcess, materialProcess, roughProcess, 
            coreProcess, cavityProcess
        ));
    }

    private void initMaterialData() {
        // 初始化一些辅料数据
        Material material1 = new Material();
        material1.setName("阀针");
        material1.setSpecification("S-25");
        material1.setStock(100);
        material1.setSafeStock(50);
        material1.setUnit("件");
        material1.setSupplier("上海精密器件有限公司");
        material1.setLastUpdate(LocalDateTime.now());

        Material material2 = new Material();
        material2.setName("弹簧");
        material2.setSpecification("D-30");
        material2.setStock(30);
        material2.setSafeStock(40);
        material2.setUnit("件");
        material2.setSupplier("上海弹簧制造厂");
        material2.setLastUpdate(LocalDateTime.now());

        Material material3 = new Material();
        material3.setName("导向柱");
        material3.setSpecification("Z-40");
        material3.setStock(60);
        material3.setSafeStock(30);
        material3.setUnit("根");
        material3.setSupplier("精密模具配件有限公司");
        material3.setLastUpdate(LocalDateTime.now());

        Material material4 = new Material();
        material4.setName("顶针");
        material4.setSpecification("T-15");
        material4.setStock(0);
        material4.setSafeStock(20);
        material4.setUnit("件");
        material4.setSupplier("华南模具配件厂");
        material4.setLastUpdate(LocalDateTime.now());

        materialRepository.saveAll(Arrays.asList(
            material1, material2, material3, material4
        ));
    }
}
