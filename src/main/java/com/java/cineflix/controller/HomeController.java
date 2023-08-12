package com.java.cineflix.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.cineflix.pojo.Category;
import com.java.cineflix.pojo.Phim;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@GetMapping("")
	public ModelAndView home() {
		ModelAndView andView = new ModelAndView("home.html");
		
		String responePhim = getDataTypeGet("http://localhost:8080/phims/infoPhim");
		String responeCategory = getDataTypeGet("http://localhost:8080/category");
		ObjectMapper mapper = new ObjectMapper();
		try {
			Phim[] phims = mapper.readValue(responePhim, Phim[].class);
			Category[] categories = mapper.readValue(responeCategory, Category[].class);
			andView.addObject("phims", phims);
			andView.addObject("categorys", categories);
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return andView;
	}
	
//	get data from API
	private String getDataTypeGet(String url) {
		StringBuilder responeData = new StringBuilder();
		
		try {
			URL newUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) newUrl.openConnection();
			connection.setRequestMethod("GET");
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				responeData.append(line);
			}
			
			bufferedReader.close();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return responeData.toString();
	}
	
}
