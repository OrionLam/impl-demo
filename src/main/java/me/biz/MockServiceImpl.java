package me.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MockServiceImpl implements MockBizService {

	@Override
	public MockDTO save(MockDTO dto) {
		System.out.println("save mockDTO biz implement");
		if ( dto==null ) {
			dto = new MockDTO();
		}
		dto.setPk(UUID.randomUUID().toString());
		dto.setName("Mock");
		return dto;
	}

	@Override
	public List<MockDTO> queryByName(String name) {
		System.out.println("queryByName mockDTO biz implement");
		List<MockDTO> result = new ArrayList<>();
		for (int i=0;i<8;i++) {
			MockDTO m = new MockDTO();
			m.setPk(UUID.randomUUID().toString());
			m.setName(name+(new Random()).nextInt(100));
			result.add(m);
		}
		return result;
	}

}
