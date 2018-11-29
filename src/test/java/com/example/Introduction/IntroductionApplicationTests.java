package com.example.Introduction;

import com.example.introduction.gen.entity.Task;
import com.example.introduction.gen.entity.TaskExample;
import com.example.introduction.gen.mapper.TaskMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class IntroductionApplicationTests {

	@Autowired
	TaskMapper mapper;

	/**
	 * MyBatis+Springの連携テスト。
	 * Generatorで生成したファイルでDBにアクセスできることを確認する。
	 */
	@Test
	public void checkIntegrationWithMyBatis() {
		Assert.assertNotNull(mapper);
		Task task = new Task();
		task.setText("text text");
		task.setTitle("title");

		int result = mapper.insertSelective(task);


		Assert.assertEquals(1, result);

		TaskExample example = new TaskExample();
		example.createCriteria();
		List<Task> foundTasks = mapper.selectByExampleWithBLOBs(example);
		Assert.assertEquals(1, foundTasks.size());

		Task found = foundTasks.get(0);
		Assert.assertEquals("text text", found.getText());

	}

}
