/**
 * 
 */
package com.li.test.brief;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.we.bean.ProjectBrief;
import com.we.bean.ProjectBriefDto;
import com.we.brief.service.IBriefService;

/**
 * @author liliu
 *
 */

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = {"classpath:spring-mvc.xml",//"classpath:datasources/*.xml",
        "classpath:spring-mybatis.xml","classpath:securityContext.xml"})
public class TestBrief {

	 @Autowired
	 private IBriefService briefService;
	 
	public TestBrief() {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws SQLException {
		ProjectBriefDto brief = new ProjectBriefDto();
		brief.setBriefId(1);
		brief.setBriefName("zhengsan");
//		1add
//		int birefc=briefService.save(biref);
//		System.out.println("test data "+birefc);
//		2mody
//		int birefM=briefService.submit(biref);
//		System.out.println("test data "+birefM);
//		3search
//		List<ProjectBrief> birefList=briefService.listDetailInfo(brief);
//		for(ProjectBrief a:birefList){
//			System.out.println("test data "+a.getProjectBriefID()+":"+a.getProjectBriefName());
//		}
//		4 delete
//		int birefD=briefService.deleteReceivedReports(1);
//		System.out.println("test data "+birefD);
//		5Detail
		ProjectBrief birefDetail=(ProjectBrief) briefService.getBriefDetail(1);
		System.out.println("test data "+birefDetail.getProjectBriefID()+":"+birefDetail.getProjectBriefName());
//		fail("Not yet implemented");
//		System.out.println("test data ");
	}

}
