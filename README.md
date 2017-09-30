# SmartChart

------

SmartChart是Android图表框架，现在支持线性图（折线，曲线，散点）柱状图，支持多样化配置。

## 视频

![github](gifeditor_20170930_170556.gif)


## 功能介绍


    
   支持轴方向，双轴，图示，水平线，十字轴，MarkView自定义，空白，标题，网格等，支持丰富的样式，包括字体样式（字体大小，颜色），图形样式（正方形，长方形，圆形），线（大小，颜色，DashPathEffect）,增加了图表移动和缩放功能以及动画。
   
  ### 如何使用

> * Step 1. Add the JitPack repository to your build file
```gradle
allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```
> *Step 2. Add the dependency
```gradle
dependencies {
	        compile 'com.github.huangyanbin:SmartChart:0.7'
	}
```
    
#### 1. 使用图表 

    ```xml
    <!--柱状图-->
    <com.daivd.chart.core.ColumnChartView
       android:id="@+id/columnChart"
       android:layout_width="match_parent"
       android:background="#f4f4f4"
       android:layout_height="320dp"/>
    <!--线型图-->   
    <com.daivd.chart.core.LineChartView
       android:id="@+id/lineChart"
       android:layout_width="match_parent"
       android:background="#f4f4f4"
       android:layout_height="320dp"
       android:layout_marginBottom="5dp"/>
    ```
    
####  2.曲线图

 lineChartView.setLineModel(LineChartView.CURVE_MODEL);
    
#### 3. 设置轴方向 

    ```java
      BaseAxis verticalAxis =  lineChartView.getLeftVerticalAxis();
        BaseAxis horizontalAxis=  lineChartView.getHorizontalAxis();
        //设置竖轴方向
        verticalAxis.setAxisDirection(AxisDirection.LEFT);
        //设置网格
        verticalAxis.setDrawGrid(true);
        //设置横轴方向
        horizontalAxis.setAxisDirection(AxisDirection.BOTTOM);
        horizontalAxis.setDrawGrid(true);
        //设置线条样式
        verticalAxis.getLineStyle().setWidth(this,1);
    ```

#### 4. 开启缩放

      ```java
     chartView.setZoom(true);
    ```
#### 5. 图表内容样式和功能

      ```java
       //开启十字架
        lineChartView.getProvider().setOpenCross(true);
        //开启MarkView
        lineChartView.getProvider().setOpenMark(true);
        //设置MarkView
        lineChartView.getProvider().setMarkView(new MsgMarkView(this));
        //设置显示点
        lineChartView.getProvider().setShowPoint(true);
        //设置显示点的样式
        lineChartView.getProvider().getPointStyle().setShape(PointStyle.CIRCLE);
       
    ```
#### 6. 图示

      ```java
        //设置图示方向
       lineChartView.getLegend().setLegendDirection(ILegend.BOTTOM);    //设置图示样式
        lineChartView.getLegend().getLegendStyle().setShape(PointStyle.RECT);
        //设置图示比例
         lineChartView.getLegend().setLegendPercent(0.2f);
       
    ```
### 7.标题  

     ```java
      //设置显示标题
        lineChartView.setShowChartName(true);
        //设置标题方向
        lineChartView.getChartTitle().setTitleDirection(IChartTitle.BOTTOM);
        //设置标题比例
        lineChartView.getChartTitle().setTitlePercent(0.2f);
        //设置标题样式
        lineChartView.getChartTitle().getTextStyle().setTextColor(res.getColor(R.color.arc21));
       
    ```
### 8.数据设置

     ```java
     //Y轴数据
    List<String> chartYDataList = new ArrayList<>();
        chartYDataList.add("华北");
        chartYDataList.add("华中");
        chartYDataList.add("华东");
        chartYDataList.add("华西");
        //X轴数据
        List<ColumnData> ColumnDatas = new ArrayList<>();
        ArrayList<Double> tempList1 = new ArrayList<>();
        tempList1.add(26d);
        tempList1.add(35d);
        tempList1.add(40d);
        tempList1.add(10d);
        ColumnData columnData1 = new ColumnData("温度","℃",AxisDirection.RIGHT,getResources().getColor(R.color.arc3),tempList1);
        ArrayList<Double> humidityList = new ArrayList<>();
        humidityList.add(60d);
        humidityList.add(50d);
        humidityList.add(30d);
        humidityList.add(65d);
        ColumnData columnData2 = new ColumnData("湿度","RH%",getResources().getColor(R.color.arc2),humidityList);
        ColumnDatas.add(columnData1);
        ColumnDatas.add(columnData2);
        ChartData chartData = new ChartData("线型图",chartYDataList,ColumnDatas);
        //设置数据
        lineChartView.setChartData(chartData);
 ```


###  9.动画

     ```java
     //你可以使用默认动画 也可以设置Interpolator
     //startChartAnim(int duration, Interpolator interpolator)
      lineChartView.startChartAnim(1000);
 ```
 
### 10.下版本0.8
    
        1.支持点击所有属性的回调
	2.支持图表轴单位显示
        2.新增饼图,雷达图
	3.修复之前的已知的bug.
        
    
   
 

### 11.结尾
    
    项目包括还有很多配置项，这里不一一列举，包括设置Padding等。
    做到几乎所有图表属性都可以配置，但由于这个框架只开发了3天，还有很多待完善的地方。再一次感谢您花费时间阅读。
 



