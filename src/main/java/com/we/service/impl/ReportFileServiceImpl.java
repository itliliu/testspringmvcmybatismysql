package com.we.service.impl;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.we.bean.ReportFile;
import com.we.dao.ReportFileMapper;
import com.we.service.IReportFileService;

@Service("reportFileService")
public class ReportFileServiceImpl implements IReportFileService {

	@Resource
	private ReportFileMapper reportFileMapper;
	
	/**
	 * Insert report file info to table ReportFile.
	 * @param record
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int createReportFile(Map<String,Object> record) throws SQLException {
		// TODO Auto-generated method stub
		return this.reportFileMapper.createReportFile(record);
	}
	
	/**
	 * Update report file's url to table ReportFile.
	 * @param record
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int updateReportFile(ReportFile record) throws SQLException {
		// TODO Auto-generated method stub
		return this.reportFileMapper.updateReportFile(record);
	}
	
	
	/**
	 * Get report file info.
	 * @param reportID
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Map<String, Object> getReportFilePath(int reportID) throws SQLException {
		// TODO Auto-generated method stub
		return this.reportFileMapper.getReportFilePath(reportID);
	}

}
