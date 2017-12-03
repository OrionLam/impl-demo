package me.biz;

import java.util.List;

public interface MockBizService {
	
	MockDTO save(MockDTO dto);
	
	List<MockDTO> queryByName(String name);
}
