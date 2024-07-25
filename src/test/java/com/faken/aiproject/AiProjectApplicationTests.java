package com.faken.aiproject;

import com.faken.aiproject.util.HuaweiOBSUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AiProjectApplicationTests {

    @Autowired
    private HuaweiOBSUtils huaweiOBSUtils;

    @Test
    void contextLoads() {
//        huaweiOBSUtils.uploadToOBS(new File("HELP.md"), "1.txt");
    }

    @Test
    void testModelRank() {

    }

}
