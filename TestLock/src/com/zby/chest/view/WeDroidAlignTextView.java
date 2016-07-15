package com.zby.chest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.TextView;

public class WeDroidAlignTextView extends TextView { 
    private boolean first = true;   
    
    public WeDroidAlignTextView(Context context, AttributeSet attrs, int defStyle) {   
        super(context, attrs, defStyle);    
        getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {      
            @Override      
            public boolean onPreDraw() {        
                initTextInfo();        
                return true;      
                }    
            });  
        }   
    
    public WeDroidAlignTextView(Context context, AttributeSet attrs) {    
        this(context, attrs, 0);  
    }   
    
    public WeDroidAlignTextView(Context context) {    
        this(context, null, 0);  
        }   
    
    private float textSize;  
    private float textLineHeight;  
    private int top;  
    private int y;  
    private int lines;  
    private int bottom;  
    private int right;  
    private int left;  
    private int lineDrawWords;  
    private char[] textCharArray;  
    private float singleWordWidth;   
    private float lineSpacingExtra;   
    
    
    public void initTextInfo() {    
        textSize = getTextSize();    
        textLineHeight = getLineHeight();    
        left = 0;    
        right = getRight();    
        y = getTop();    
        // Ҫ���Ŀ���   
        int drawTotalWidth = right - left;    
        String text = getText().toString();    
        if (!TextUtils.isEmpty(text) && first) {      
            textCharArray = text.toCharArray();      
            TextPaint mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);      
            mTextPaint.density = getResources().getDisplayMetrics().density;      
            mTextPaint.setTextSize(textSize);      
            // һ�����ʵĵĿ���      
            singleWordWidth = mTextPaint.measureText("һ") + lineSpacingExtra;      
            // һ�п��ԷŶ��ٸ��ַ�      
            lineDrawWords = (int) (drawTotalWidth / singleWordWidth);      
            int length = textCharArray.length;      
            lines = length / lineDrawWords;      
            if ((length % lineDrawWords) > 0) {        
                lines = lines + 1;      
                }      
            
            first = false;     
            MarginLayoutParams layoutParams = (MarginLayoutParams) getLayoutParams();      
            int totalHeight = (int) (lines*textLineHeight+textLineHeight*2 + getPaddingBottom()+getPaddingTop()+layoutParams.bottomMargin+layoutParams.topMargin);      
            setHeight(totalHeight);    
            }  
        }   
    
    @Override  protected void onDraw(Canvas canvas) {    
        bottom = getBottom();    
        int drawTotalLine = lines;     
        if(maxLine!=0&&drawTotalLine>maxLine){      
            drawTotalLine = maxLine;   
            }     
        for (int i = 0; i < drawTotalLine; i++) {      
            try {        
                int length = textCharArray.length;        
                int mLeft = left;        
                // ��i+1�п�ʼ���ַ�index        
                int startIndex = (i * 1) * lineDrawWords;        
                // ��i+1�н������ַ�index        
                int endTextIndex = startIndex + lineDrawWords;        
                if (endTextIndex > length) {          
                    endTextIndex = length;          
                    y += textLineHeight;        
                } else {          y += textLineHeight;        
                }        
                
                for (; startIndex < endTextIndex; startIndex++) {          
                    char c = textCharArray[startIndex];
                    //         if (c == ' ') {//           c = '\u3000';//         } else if (c < '\177') {//           c = (char) (c + 65248);//         }          
//                    if(textCharArray[i]=='\n'){
//                        mLeft=left;
//                        y += textLineHeight;    
//                    }
                    
                    canvas.drawText(String.valueOf(c), mLeft, y, getPaint());          
                    mLeft += singleWordWidth;        
                }     
            } catch (Exception e) {        
                e.printStackTrace();      
                }    
            }  
        }   
    
    int maxLine;   
    public void setMaxLines(int max){    this.maxLine = max;  }   
    
    public void setLineSpacingExtra(int lineSpacingExtra){    
        this.lineSpacingExtra = lineSpacingExtra;  
        
    }    
    
    /**    * �ж��Ƿ�Ϊ����    * @return    */  
    public static boolean containChinese(String string){     
        boolean flag = false;     
        for (int i = 0; i < string.length(); i++) {       
            char c = string.charAt(i);       
            if ((c >= 0x4e00) && (c <= 0x9FA5)) {         
                flag = true;       
                }     
            }     
        return flag;   
        }    
    
    public static String ToDBC(String input) {    
        // ����TextView�쳣���е�ԭ�򣺰�׿Ĭ�����֡���ĸ����Ϊ��һ���Ժ�ÿ�еĿ�ͷ�ַ�����Ϊ���֡���ĸΪ����ַ�    // ��������ֻ��Ҫ������ַ�ת��Ϊȫ���ַ�����    
        char c[] = input.toCharArray();    
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';      
            } else if (c[i] < '\177') {        
                    c[i] = (char) (c[i] + 65248);      
            }   
        }    
        return new String(c);  
    }
}