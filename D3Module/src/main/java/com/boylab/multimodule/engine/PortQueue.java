package com.boylab.multimodule.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 1、数组模式
 * 2、队列模式
 * @author pengle
 *
 */
public class PortQueue {

	/**
	 * 1、添加到循环中
	 * 2、从循环中删除
	 * 3、从循环中按顺序取命令
	 */
	private int marker = 0;
	private List<Integer> slaveList = new ArrayList<Integer>();
	private HashMap<Integer, Object> objMap = new HashMap<>();

	public void addPort() {
		
	}

	public void removePort() {
		
	}
	
	public void getCmd() {
		
	}
	
}
