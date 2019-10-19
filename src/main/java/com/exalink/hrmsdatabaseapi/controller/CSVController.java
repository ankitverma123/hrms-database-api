package com.exalink.hrmsdatabaseapi.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exalink.hrmsdatabaseapi.entity.Report;
import com.exalink.hrmsdatabaseapi.repository.IReportRepository;
import com.exalink.hrmsfabric.common.BaseException;
import com.exalink.hrmsfabric.utils.CSVUtils;

/**
 * @author ankitkverma
 *
 */
@RestController
@RequestMapping("/csv")
public class CSVController {

	@Autowired
	private IReportRepository reportRepo;

	@GetMapping(value = "/sample/{reportName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void downloadSample(@PathVariable String reportName, HttpServletResponse response) throws BaseException, IOException {
		Optional<Report> reportTemplate = reportRepo.findByReportName(reportName);
		if (reportTemplate.isPresent()) {
			String keys = reportTemplate.get().getKeys();
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=Comprehensive Summary.csv");
			CSVUtils.writeDataToCsvReporting(response.getWriter(), null, keys.split(","), null);
		}
	}

}
