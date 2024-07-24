package com.faken.aiproject;

import com.faken.aiproject.controller.ModelController;
import com.faken.aiproject.po.result.Result;
import com.faken.aiproject.po.vo.ModelRankVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AiProjectApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testModelRank() {
        Result<List<ModelRankVO>> modelRankVOResult = (Result<List<ModelRankVO>>) new ModelController().modelRank();
    }

}
