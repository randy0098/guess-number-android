package com.randy.guessnumber;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

public class PlayActivity extends Activity {
	public static String generatedNumber; 

	public void onCreate(Bundle savedInstanceState) {
		//随机生成4位数字
		generatedNumber = PlayActivity.generateNumbers(4);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);

        Button confirmButton = (Button) findViewById(R.id.button1);
        confirmButton.setOnClickListener(
        	new OnClickListener(){
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
			        EditText editText= (EditText) findViewById(R.id.editText1);
			        String inputNumber = editText.getText().toString();
			        //清除输入数字
			        editText.setText("");
			        //如果用户输入的数字不够4位就在前端补0
			        if(inputNumber.length()==0){
			        	inputNumber = "0000";
			        }else if(inputNumber.length()==1){
			        	inputNumber = "000" + inputNumber;
			        }else if(inputNumber.length()==2){
			        	inputNumber = "00" + inputNumber;
			        }else if(inputNumber.length()==3){
			        	inputNumber = "0" + inputNumber;
			        }
			        //剩余次数减1
		        	TextView text1= (TextView) findViewById(R.id.text1);
			        String leftTimes = text1.getText().toString();
			        text1.setText(String.valueOf(Integer.parseInt(leftTimes)-1));
			        //获得比较结果
			        String compareResult = PlayActivity.compareNumbers(inputNumber,generatedNumber);
		        	TextView text3= (TextView) findViewById(R.id.text3);
		        	String resultList = text3.getText().toString();
		        	resultList = resultList + inputNumber + "→" + compareResult + "\n";
			        text3.setText(resultList);
			        //没有猜对数字时
			        if(compareResult.equalsIgnoreCase("4A0B") == false){
			        	//剩余次数大于0时
				        if(Integer.parseInt(leftTimes)-1>0){
					        //生成结果信息
					        String result = "没有猜中数字，\n请再接再励！";
				        	TextView text2= (TextView) findViewById(R.id.text2);
					        text2.setText(result);
			        	}
				        //没有剩余次数时结束游戏
				        else{
				        	//隐藏第一行
				        	TableRow row1 = (TableRow) findViewById(R.id.tableRow1);
				        	row1.setVisibility(4);
				        	//显示重新开始游戏按钮
					        String result = "正确数字为\n"+generatedNumber;
				        	TextView text2= (TextView) findViewById(R.id.text2);
					        text2.setText(result);
				        	Button button2 = (Button) findViewById(R.id.button2);
				        	button2.setVisibility(0);
				        }

			        }
			        //猜对数字时
			        else{
			        	//隐藏第一行
			        	TableRow row1 = (TableRow) findViewById(R.id.tableRow1);
			        	row1.setVisibility(4);
			        	//显示重新开始游戏按钮
				        String result = "数字正确，\n您真聪明！";
			        	TextView text2= (TextView) findViewById(R.id.text2);
				        text2.setText(result);
			        	Button button2 = (Button) findViewById(R.id.button2);
			        	button2.setVisibility(0);
			        }
				}
        	}
        );
        
        //重新开始游戏
        Button restartButton = (Button) findViewById(R.id.button2);
        restartButton.setOnClickListener(
        	new OnClickListener(){
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					PlayActivity.this.finish();
					Intent intent = new Intent();
					intent.setClass(PlayActivity.this, PlayActivity.class);
					startActivity(intent);
				}
        	}
        );
	}

	// 随机生成digit位数字，并且确保每位数字各不相同
	public static String generateNumbers(int digit) {
		HashMap generatedNums = new HashMap();
		String result = "";
		
		while(generatedNums.size()<digit){
			int num = (int) (Math.random() * 10);
			if(generatedNums.containsKey(num)==false){
				result = result + new Integer(num).toString();
				generatedNums.put(num, num);
			}
		}
		return result;
	}
	
	
	//比较num1和num2是否相等
	public static String compareNumbers(String num1,String num2){
		HashMap existNumIndex = new HashMap();
		int numA = 0;
		int numB = 0;
		for(int i=0; i<num1.length(); i++){
			String n1 = num1.substring(i, i+1);
			for(int j=0; j<num2.length(); j++){
				String n3 = num2.substring(i, i+1);
				//如果n1,n2相同位置的数字相同时就记录为一个A
				if(n1.equalsIgnoreCase(n3)==true){
					existNumIndex.put(i, i);
					numA++;
					break;
				}
				//如果n1,n2相同位置的数字不相同时
				//跳过原来比较时的相同数字
				if(existNumIndex.containsKey(j) == true){
					continue;
				}
				//不是原来比较时的相同数字
				String n2 = num2.substring(j, j+1);
				//如果此次数字不相同就转向下个数字
				if(n1.equalsIgnoreCase(n2) == false){
					continue;
				}
				//如果n1,n2数字相同
				else if(n1.equalsIgnoreCase(n2)==true){
					//如果n1,n2数字相同，并且n2不会成为以后比较时发现的A时，就记录一个B
					String n4 = num1.substring(j, j+1);
					if(n2.equalsIgnoreCase(n4) == false){
						existNumIndex.put(j, j);
						numB++;
						break;
					}
					//如果n1,n2数字相同，并且n2会成为以后比较时发现的A时，就跳过此数字
					else{
						continue;
					}
				}
			}
		}
		String result = new Integer(numA).toString()+"A"+new Integer(numB).toString()+"B";
		return result;
	}
}
