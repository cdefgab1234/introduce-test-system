package it.com.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONArray;

/**
 * 随机生成常见的汉字
 * 
 * @author xuliugen
 * 
 */
@Controller
public class GeneCharTest {
  /*  public static void main(String[] args) {
            System.out.print(getRandomChar() + "  ");
    }*/
	@RequestMapping(value="yzmch.action")
    private static void getRandomChar(HttpSession session,HttpServletResponse resp,HttpServletRequest request) throws IOException  {
        String str = "";
        int hightPos; //
        int lowPos;

        Random random = new Random();

       List list=new ArrayList();

        try {
        	  for (int i = 1; i <10; i++) {
	        	 hightPos = (176 + Math.abs(random.nextInt(39)));
	             lowPos = (161 + Math.abs(random.nextInt(93)));
	
	             byte[] b = new byte[2];
	             b[0] = (Integer.valueOf(hightPos)).byteValue();
	             b[1] = (Integer.valueOf(lowPos)).byteValue();
	             str = new String(b, "GBK");
	             list.add(str.charAt(0));
        	  }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("错误");
        }
        System.out.println(list);
        JSONArray jsonArray=JSONArray.fromObject(list);
        PrintWriter out=resp.getWriter();
        out.println(jsonArray);
    }
}