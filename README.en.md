# ![icon](/img/icon.png)SmartChart

------
* [Chinese README](/README.md/)

* SmartChart is an Android chart framework that supports linear diagrams (broken lines, curves, scatter points) bar charts, area charts, pie charts, and 3D columnar diagrams to support a variety of configurations.

## Function display

####  1. Line chart
![The line chart](/img/line1.png)
####  2.  Graph chart
![曲线图](/img/line2.png)
####  3. Scatter chart
![散点图](/img/line3.png)
####  4.Area chart
![面积图](/img/line4.png)
####  5.Bar chart
![柱状图](/img/bar1.png)
####  6.3D bar chart
![3D柱状图](/img/bar2.png)
####  7.Pie chart
![饼图](/img/pie.png)
####  8.Radar chart
![雷达图](/img/radar.png)
####  9.Rose chart
![玫瑰图](/img/rose1.png)
### 10.DashBoradChart
![仪表盘](/img/dashBorad.png)
*  note: the dashboard is intended to be finished, but it has been used in an example, and the wheel is not repeated.
https://github.com/huangyanbin/CalendarView
there is a need for reference.

#### Chart anim

![动画](/img/gif/chart.gif)

#### Chart zoom

![缩放移动](/img/gif/zoom.gif)

#### Chart rotate

![旋转](/img/gif/rotate.gif)

### Legend position
![Legend位置](/img/rose2.png)

## Function introduce

* Support shaft direction, biaxial, graphic, horizontal line, cross shaft, MarkView custom, blank, the title, grid, such as support rich style, including the font style, font size, color, graphic style (square, rectangle, round), line (size, color, DashPathEffect), increase the movement and zoom function diagram and animation.

### 1.0 version update log
* 1. Increased wind rose chart;
* 2. Fixed the Legend location display problem;
* 3. Increase the format of the scale.

### 0.9 version update log

* 1. Repair axis and grid display incomplete problem;
* 2. Increase the rotation gesture of radar map;
* 3. Fix the title display problem;
* 4. Repair curve and broken line dynamic switch collapse problem.

### 0.8 version update log

* 1. New scatter plots, area charts, pie charts, 3D column graphs;
* 2. To support the rotation and selection of the pie chart;
* 3. Fixed 0.7 bug before repair;
* 4. Refactor the code to support more chart types.

### 0.7 version update log

* 1. New scatter plots, area charts, pie charts, 3D column graphs;
* 2. To support the rotation and selection of the pie chart;
* 1. Support linear graph (line, curve, scatter) bar chart,
* 2. Support chart axis, legend, graphical, horizontal, cross-axis, MarkView customization, blank, title, grid, etc
* 3. Support rich styles, including font style (font size, color), graphic style (square, rectangle, circle), line (size, color, DashPathEffect)
* 4. Add chart movement and zoom function and animation.
* 4. Refactor the code to support more chart types.

  ### How to use

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
    
#### 1. Use the chart

    ```xml
    <!--柱状图-->
    <com.daivd.chart.core.ColumnChart
       android:id="@+id/columnChart"
       android:layout_width="match_parent"
       android:background="#f4f4f4"
       android:layout_height="320dp"/>
    <!--线型图-->   
    <com.daivd.chart.core.LineChart
       android:id="@+id/lineChart"
       android:layout_width="match_parent"
       android:background="#f4f4f4"
       android:layout_height="320dp"
       android:layout_marginBottom="5dp"/>
    ```
    
####  2.The graph

 lineChartView.setLineModel(LineChartView.CURVE_MODEL);
    
#### 3. Set axis

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

#### 4. Open the zoom

      ```java
     chartView.setZoom(true);
    ```
#### 5. Chart content styles and functions

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
#### 6. Legend

      ```java
        //设置图示方向
       lineChartView.getLegend().setLegendDirection(ILegend.BOTTOM);    //设置图示样式
        lineChartView.getLegend().getLegendStyle().setShape(PointStyle.RECT);
        //设置图示比例
         lineChartView.getLegend().setLegendPercent(0.2f);
       
    ```
### 7.Chart Title  

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
### 8.Chart Data Set

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


###  9.Anim

     ```java
     //你可以使用默认动画 也可以设置Interpolator
     //startChartAnim(int duration, Interpolator interpolator)
      lineChartView.startChartAnim(1000);
    ```
 
### 10.Next version 1.1
    
* 1. Fix the view pager and list sliding to cause gesture conflict;
* 2. Continuous optimization of chart display effects;
* 3. Support lines and histogram composition diagrams.
        
    
### 11.End
    
* the project includes a number of configuration items, which are not listed here, including setting up Padding.
Almost all of the chart attributes can be configured, but since this framework has only been developed for three days, there is still a lot to be done. Thank you again for spending time reading.



