package com.example.demo.util;

import com.example.demo.annotation.ExportIndex;
import com.example.demo.annotation.ImportIndex;
import com.example.demo.annotation.ValidateType;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: liuxiangtao90
 * @Date: 2018/12/29 14:13
 * @Description: excel工具类
 */
public final class ExcelUtil {

    private final static Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    private ExcelUtil(){}

    /**
     * @Decription: 批量导出
     * @Author: liuxiangtao90
     * @CreateDate: Created in 2018/12/29 15:21
     * @param:
     * @param request
     * @param response
     * @param list
     * @param sheetName
     * @param fileName
     * @Return: void
     */
    public static <T> void exportExcelList(HttpServletRequest request, HttpServletResponse response, List<T> list,
                                           String sheetName, String fileName) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        try {
            workbook.createSheet(sheetName);
            int rowNum = 0;
            HSSFSheet hssfSheet = workbook.getSheet(sheetName);
            hssfSheet.autoSizeColumn(1);
            HSSFRow row = hssfSheet.createRow(rowNum++);
            // 处理表头行  无可导出数据在前台校验
            Field[] field= list.get(0).getClass().getDeclaredFields();
            for (int i = 0; i < field.length; i++) {
                ExportIndex ei = field[i].getAnnotation(ExportIndex.class);
                if (ei == null)
                    continue;
                HSSFCell cell = row.createCell(ei.index());
                cell.setCellValue(ei.name());
            }
            // 处理数据行
            for (int k = 0; k < list.size(); k++) {
                HSSFRow rowTemp = hssfSheet.createRow(rowNum++);
                Field[] fieldTemp = list.get(k).getClass().getDeclaredFields();
                for (int i = 0; i < field.length; i++) {
                    try {
                        ExportIndex ei = fieldTemp[i].getAnnotation(ExportIndex.class);
                        if (ei == null) {
                            continue;
                        }
                        HSSFCell cell = rowTemp.createCell(ei.index());
                        String name = fieldTemp[i].getName().substring(0, 1).toUpperCase() + fieldTemp[i].getName().substring(1);
                        Method m = list.get(k).getClass().getMethod("get" + name);
                        Object value = m.invoke(list.get(k));
                        if (value != null && ei.type().equals(ValidateType.TIMESTAMP)) {
                            value = TimeUtil.timeStamp2Str(TimeUtil.SECOND1, Integer.parseInt(value + ""));
                        }
                        if (value != null &&ei.type().equals(ValidateType.DATESTAMP)) {
                            value = TimeUtil.timeStamp2Str(TimeUtil.DAY1, Integer.parseInt(value + ""));
                        }
                        else if (value != null && ei.type().equals(ValidateType.DATE)) {
                            value=TimeUtil.format(TimeUtil.DAY1, value);
                        }
                        else if(value != null && ei.type().equals(ValidateType.DATETIME)) {
                            value=TimeUtil.format(TimeUtil.SECOND1,value);
                        }
                        if (value != null && ei.type().equals(ValidateType.Double)) {
                            value = new BigDecimal(Double.parseDouble(value + "")).setScale(2,BigDecimal.ROUND_HALF_UP)+"";
                        }
                        cell.setCellValue(value == null ? "" : value+"");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        log.error("{}",e);
                    }
                }
            }
            response.reset();
            fileName = fileName + TimeUtil.now2Str(TimeUtil.DAY2)+".xls";
            response.setContentType("application/force-download");// 设置强制下载不打开
            String userAgent = request.getHeader("User-Agent");
            byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes() : fileName.getBytes("UTF-8");
            String name = new String(bytes, "ISO-8859-1");
            response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", name));
            workbook.write(response.getOutputStream());
        }
        catch (Exception e) {
            log.error("{}",e);
        }
    }

    /**
     * 导出Excel模板
     * @param request
     * @param response
     */
    public static void exportExcel (HttpServletRequest request, HttpServletResponse response,
                                    String[] titleRow, String sheetName, String fileName) {
        FileInputStream fis = null;
        OutputStream os = null;
        String path = request.getSession().getServletContext().getRealPath("")
                + "/"+fileName+".xls";
        File file = new File(path);
        if (!file.exists()) {
            new File(request.getSession().getServletContext().getRealPath("")).mkdirs();
            HSSFWorkbook  workbook = new HSSFWorkbook();
            FileOutputStream out = null;
            try {
                workbook.createSheet(sheetName);
                int rowNum = 0;
                HSSFRow row = workbook.getSheet(sheetName).createRow(rowNum++);    //创建第一行
                for (int i = 0; i < titleRow.length; i++){
                    HSSFCell cell = row.createCell(i);
                    cell.setCellValue(titleRow[i]);
                }
                out = new FileOutputStream(path);
                workbook.write(out);
            }
            catch (Exception e) {
                log.error("{}",e);
            }
            finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    log.error("{}",e);
                }
            }
        }
        try {
            response.reset();
            fileName = fileName+".xls";
            response.setContentType("application/force-download");// 设置强制下载不打开
            String userAgent = request.getHeader("User-Agent");
            byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes() : fileName.getBytes("UTF-8");
            String name = new String(bytes, "ISO-8859-1");
            response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", name));

            File fileTemp = new File(path);
            fis = new FileInputStream(fileTemp);
            os = response.getOutputStream();
            byte[] buffer = new byte[1024];
            while ((fis.read(buffer)) != -1) {
                os.write(buffer);
            }
        }
        catch (Exception e)
        {
            log.error("{}",e);
        }
        finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (os != null) {
                    os.close();
                }
            }
            catch (Exception e) {
                log.error("{}",e);
            }
        }
    }

    /**
     * 创建新excel.
     * @param fileDir  excel的路径
     * @param sheetName 要创建的表格索引
     * @param titleRow excel的第一行即表格头
     */
    public  static<T> void createExcel(String fileDir, String sheetName, String titleRow[], String[] column, List<T> t) {
        //创建workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
        workbook.createSheet(sheetName);
        //新建文件
        FileOutputStream out = null;
        try {
            int rowNum = 0;
            //添加表头
            HSSFRow row = workbook.getSheet(sheetName).createRow(rowNum++);    //创建第一行
            for (int i = 0; i < titleRow.length; i++){
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(titleRow[i]);
            }
            for (int beanIndex = 0; beanIndex < t.size(); beanIndex++)
            {
                row = workbook.getSheet(sheetName).createRow(rowNum++);
                for (int cIndex=0; cIndex < column.length; cIndex++)
                {
                    T beanClass=t.get(beanIndex);
                    String name = column[cIndex].substring(0, 1).toUpperCase() + column[cIndex].substring(1);
                    Method m = beanClass.getClass().getMethod("get" + name);
                    Object value = m.invoke(beanClass); // 调用getter方法获取属性值
                    HSSFCell cell = row.createCell(cIndex);
                    cell.setCellValue(value+"");
                }
            }
            out = new FileOutputStream(fileDir);
            workbook.write(out);
        } catch (Exception e) {
            log.error("{}",e);
        } finally {
            try {
                if (out != null) {
                out.close();
                }
            } catch (IOException e) {
                log.error("{}",e);
            }
        }
    }

    /**
     * 删除文件.
     * @param fileDir  文件路径
     */
    public static boolean deleteExcel(String fileDir) {
        File file = new File(fileDir);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return false;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return file.delete();
            }
            return false;
        }
    }

    /**
     * 模板校验
     * @author liuxiangtao
     * @date 2018年4月20日 上午11:06:08
     *
     * @param inputStream
     * @param column
     * @return
     */
    public static boolean checkExcele(InputStream inputStream, String[] column)
    {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(inputStream);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = sheet.getRow(0);
            boolean isPass=true;
            for (int i = 0; i < column.length; i++) {
                if (!column[i].contains(row.getCell(i)+"")) {
                    isPass = false;
                    break;
                }
            }
            return isPass;
        }
        catch (IOException e) {
            log.error("{}",e);
            return false;
        }
        finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Decription:批量导入
     * @Author: liuxiangtao90
     * @CreateDate: Created in 2018/12/29 14:24
     * @param:
     * @param inputStream
     * @param bean
     * @Return: java.util.List<T>
     */
    public static<T> List<T> ImportExcel(InputStream inputStream,Class<T> bean) throws
            InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, IntrospectionException, SecurityException {
        HSSFWorkbook wb = null;
        try {
            POIFSFileSystem fs = new POIFSFileSystem(inputStream);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            log.error("{}",e);
        }
        HSSFSheet sheet = wb.getSheetAt(0);
        // HSSFRow row = sheet.getRow(0);
        int rowNum = sheet.getLastRowNum()+1;
        List<T> list = new ArrayList<>(rowNum);
        // 标题总列数
        Field[] filed = bean.getDeclaredFields();
        Map<Integer, Field> map = new HashMap<>();
        for (int i = 0; i < filed.length; i++) {
            ImportIndex excelIndex = filed[i].getAnnotation(ImportIndex.class);
            if ( excelIndex != null ) {
                int index = excelIndex.index();
                map.put(index, filed[i]);
            }
        }
        for (int i = 1; i < rowNum; i++) {
            if (isEmpty(sheet.getRow(i),filed.length)) {
                continue;
            }
            T t = bean.newInstance();
            for (int j = 0; j < map.size(); j++) {
                PropertyDescriptor pd = new PropertyDescriptor(map.get(j).getName(), bean);
                Method method = pd.getWriteMethod();
                HSSFCell cell = sheet.getRow(i).getCell(j);
                if (cell == null)
                    continue;
                Object obj;
                // 日期
                if (cell.getCellType() == 0 && DateUtil.isCellDateFormatted(cell))
                {
                    //用于转化为日期格式
                    obj = cell.getDateCellValue();
                    // obj=TimeUtil.format(TimeUtil.DAY1, d);
                }
                else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                    // 返回数值类型的值
                    Object inputValue;// 单元格值
                    Long longVal = Math.round(cell.getNumericCellValue());
                    Double doubleVal = cell.getNumericCellValue();
                    if (Double.parseDouble(longVal + ".0") == doubleVal){   //判断是否含有小数位.0
                        inputValue = longVal;
                    }
                    else {
                        inputValue = doubleVal;
                    }
                    DecimalFormat df = new DecimalFormat("##########.##");
                    obj = df.format(inputValue);
                }
                else {
                    obj = cell + "";
                }
                Class<?> type=pd.getPropertyType();
                if (type.getName().equals("java.lang.Double"))
                {
                    if ("".equals(obj) || obj == null)
                    {
                        continue;
                    }
                    method.invoke(t, Double.parseDouble(obj + ""));
                }
                else if (type.getName().equals("java.lang.Integer")) {
                    if ("".equals(obj) || obj == null) {
                        continue;
                    }
                    method.invoke(t, Integer.parseInt(obj + ""));
                }
                else if (type.getName().equals("java.util.Date")) {
                    if ("".equals(obj) || obj == null) {
                        continue;
                    }
                    if (obj instanceof String) {
                        if (((String) obj).contains("-")) {
                            obj = TimeUtil.parseDate(obj + "", TimeUtil.DAY1);
                        }
                        else if (((String) obj).contains(".")) {
                            obj = TimeUtil.parseDate(obj + "", TimeUtil.DAY3);
                        }
                    }
                    method.invoke(t, obj);
                }
                else {
                    method.invoke(t, obj);
                }
            }
            list.add(t);
        }
        try {
            inputStream.close();
        }
        catch (IOException e) {
            log.error("{}",e);
        }
        return list;
    }

    /**
     *  空行校验
     * @author liuxiangtao
     * @date 2018年4月13日 下午3:02:05
     *
     * @param row
     * @param cellNum
     * @return
     */
    private static boolean isEmpty(HSSFRow row, int cellNum) {
        if (row == null) {
            return true;
        }
        boolean result = true;
        for (int i = 0; i < cellNum; i++) {
            if (row.getCell(i) != null && !"".equals((row.getCell(i).getStringCellValue() + "").trim())) {
                result = false;
                break;
            }
        }
        return result;
    }
}
