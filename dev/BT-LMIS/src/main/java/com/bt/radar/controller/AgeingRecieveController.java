package com.bt.radar.controller;


import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.radar.controller.form.AgeingRecieveQueryParam;
import com.bt.radar.model.AgeingRecieve;
import com.bt.radar.service.AgeingRecieveService;


@Controller
@RequestMapping("/control/radar/ageingRecieveController")
public class AgeingRecieveController {


	private static final Logger logger = Logger.getLogger(AgeingRecieveController.class);
	
	@Resource(name = "ageingRecieveServiceImpl")
	private AgeingRecieveService<AgeingRecieve> ageingRecieveServiceImpl;
}
