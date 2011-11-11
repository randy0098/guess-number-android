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
		//�������4λ����
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
			        //����û���������ֲ���4λ����ǰ�˲�0
			        if(inputNumber.length()==0){
			        	inputNumber = "0000";
			        }else if(inputNumber.length()==1){
			        	inputNumber = "000" + inputNumber;
			        }else if(inputNumber.length()==2){
			        	inputNumber = "00" + inputNumber;
			        }else if(inputNumber.length()==3){
			        	inputNumber = "0" + inputNumber;
			        }
			        //��ñȽϽ��
			        String compareResult = PlayActivity.compareNumbers(inputNumber,generatedNumber);
			        //û�в¶�����ʱ
			        if(compareResult.equalsIgnoreCase("4A0B") == false){
			        	TextView text1= (TextView) findViewById(R.id.text1);
				        String leftTimes = text1.getText().toString();
			        	//ʣ���������0ʱ��ʣ�������1
				        if(Integer.parseInt(leftTimes)>0){
					        text1.setText(String.valueOf(Integer.parseInt(leftTimes)-1));
					        //���ɽ����Ϣ
					        String result = "����û�в������֣�\n���ٽ�������";
				        	TextView text2= (TextView) findViewById(R.id.text2);
					        text2.setText(result);
				        	TextView text3= (TextView) findViewById(R.id.text3);
				        	String resultList = text3.getText().toString();
				        	resultList = resultList + inputNumber + "��" + compareResult + "\n";
					        text3.setText(resultList);
			        	}
				        //û��ʣ�����ʱ������Ϸ
				        else{
				        	//���ص�һ��
				        	TableRow row1 = (TableRow) findViewById(R.id.tableRow1);
				        	row1.setVisibility(4);
				        	//��ʾ���¿�ʼ��Ϸ��ť
					        String result = "���ź���\n��û�в¶�����";
				        	TextView text2= (TextView) findViewById(R.id.text2);
					        text2.setText(result);
				        	Button button2 = (Button) findViewById(R.id.button2);
				        	button2.setVisibility(0);
				        }

			        }
			        //�¶�����ʱ
			        else{
			        	//���ص�һ��
			        	TableRow row1 = (TableRow) findViewById(R.id.tableRow1);
			        	row1.setVisibility(4);
			        	//��ʾ���¿�ʼ��Ϸ��ť
				        String result = "���Ѿ��¶������֣�\n���������";
			        	TextView text2= (TextView) findViewById(R.id.text2);
				        text2.setText(result);
			        	Button button2 = (Button) findViewById(R.id.button2);
			        	button2.setVisibility(0);
			        }
				}
        	}
        );
        
        //���¿�ʼ��Ϸ
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

	// �������digitλ����
	public static String generateNumbers(int digit) {
		String result = "";
		for (int i = 0; i < digit; i++) {
			int num = (int) (Math.random() * 10);
			result = result + new Integer(num).toString();
		}
		return result;
	}
	
	
	//�Ƚ�num1��num2�Ƿ����
	public static String compareNumbers(String num1,String num2){
		HashMap existNumIndex = new HashMap();
		int numA = 0;
		int numB = 0;
		for(int i=0; i<num1.length(); i++){
			String n1 = num1.substring(i, i+1);
			for(int j=0; j<num2.length(); j++){
				String n3 = num2.substring(i, i+1);
				//���n1,n2��ͬλ�õ�������ͬʱ�ͼ�¼Ϊһ��A
				if(n1.equalsIgnoreCase(n3)==true){
					existNumIndex.put(i, i);
					numA++;
					break;
				}
				//���n1,n2��ͬλ�õ����ֲ���ͬʱ
				//����ԭ���Ƚ�ʱ����ͬ����
				if(existNumIndex.containsKey(j) == true){
					continue;
				}
				//����ԭ���Ƚ�ʱ����ͬ����
				String n2 = num2.substring(j, j+1);
				//����˴����ֲ���ͬ��ת���¸�����
				if(n1.equalsIgnoreCase(n2) == false){
					continue;
				}
				//���n1,n2������ͬ
				else if(n1.equalsIgnoreCase(n2)==true){
					//���n1,n2������ͬ������n2�����Ϊ�Ժ�Ƚ�ʱ���ֵ�Aʱ���ͼ�¼һ��B
					String n4 = num1.substring(j, j+1);
					if(n2.equalsIgnoreCase(n4) == false){
						existNumIndex.put(j, j);
						numB++;
						break;
					}
					//���n1,n2������ͬ������n2���Ϊ�Ժ�Ƚ�ʱ���ֵ�Aʱ��������������
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
