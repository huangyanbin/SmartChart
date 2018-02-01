# ![icon](/img/icon.png)SmartChart
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![](https://www.jitpack.io/v/huangyanbin/SmartChart.svg)](https://www.jitpack.io/#huangyanbin/SmartChart)

``` SmartChart是一个Android图表框架，支持线性图（折线，曲线，散点）柱状图、面积图、饼图、3D柱状图、雷达图、风向玫瑰图,支持图表多样化配置。支持轴方向，双轴，图示，水平线，十字轴，MarkView自定义，空白，标题，网格等，支持丰富的样式，包括字体样式（字体大小，颜色），图形样式（正方形，长方形，圆形），线（大小，颜色，DashPathEffect）,增加了图表移动和缩放功能以及动画。```
* [English README](/README.en.md/)
* [apk下载地址](/img/smartChart.apk)

#### 功能介绍

######  自定义图表1

![动画](/img/zuoping.jpg)

######  手势操作

![动画](/img/gif/tip.gif)

######  1.折线图

![折线图](/img/line1.png)
######  2.曲线图

![曲线图](/img/line2.png)

######  3.散点图

![散点图](/img/line3.png)

######  4.面积图

![面积图](/img/line4.png)

######  5.柱状图

![柱状图](/img/bar1.png)

######  6.3D柱状图

![3D柱状图](/img/bar2.png)

######  7.饼图

![饼图](/img/pie.png)

######  8.雷达图

![雷达图](/img/radar.png)

######  9.玫瑰图

![玫瑰图](/img/rose1.png)

###### 10.仪表盘(Other)

![仪表盘](/img/dashBorad.png)

``` 说明：仪表盘本打算写完，但之前在一个示例中也有，就不重复造轮子了。
      https://github.com/huangyanbin/CalendarView
      有需要的可以借鉴一下。
```

###### 图表缩放移动
![缩放移动](/img/gif/zoom.gif)
###### 旋转
![旋转](/img/gif/rotate.gif)
###### Legend位置
![Legend位置](/img/rose2.png)



#### 如何使用

###### 引用

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
	        compile 'com.github.huangyanbin:SmartChart:1.4'
	}
```
    
###### 1. 导入图表View

```
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
    
######  2.曲线图

 lineChartView.setLineModel(LineChartView.CURVE_MODEL);
    
###### 3. 设置轴样式
```
    很多方法有所调整，请参考demo
```

```
        VerticalAxis verticalAxis =  lineChartView.getLeftVerticalAxis();
        HorizontalAxis horizontalAxis=  lineChartView.getHorizontalAxis();
        VerticalAxis rightAxis = lineChartView.getRightVerticalAxis();
        //是否从0开始
        rightAxis.setStartZero(false);
        //设置最大值
        rightAxis.setMaxValue(200);
        //设置最小值
        rightAxis.setMinValue(-50);
        //设置竖轴方向
        verticalAxis.setAxisDirection(IAxis.AxisDirection.LEFT);
        //设置网格
        verticalAxis.setDrawGrid(true);
         //设置横轴方向
         horizontalAxis.setAxisDirection(IAxis.AxisDirection.BOTTOM);
         //设置是否绘制网格
         horizontalAxis.setDrawGrid(true);
         //设置线条样式
         verticalAxis.getLineStyle().setWidth(mContext,1);
         DashPathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8}, 1);
          //设置网格样式
         verticalAxis.getGridStyle().setWidth(mContext,1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
         horizontalAxis.getGridStyle().setWidth(mContext,1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
```

###### 4. 开启缩放

```
     chartView.setZoom(true);
```

###### 5. 图表内容样式和功能

```
            LineStyle crossStyle = lineChartView.getProvider().getCrossStyle();
              crossStyle.setWidth(mContext,1);
              crossStyle.setColor(res.getColor(R.color.arc21));
              lineChartView.setZoom(true);
              //开启十字架
              lineChartView.getProvider().setOpenCross(true);
              //开启MarkView
              lineChartView.getProvider().setOpenMark(true);
              //设置MarkView
              lineChartView.getProvider().setMarkView(new MsgMarkView(mContext));
              //设置显示点
              lineChartView.getProvider().setShowPoint(true);
              //设置显示点的样式
              lineChartView.getProvider().getPointStyle().setShape(PointStyle.CIRCLE);
```

###### 6. 图示

```
        //设置图示方向
       lineChartView.getLegend().setLegendDirection(ILegend.BOTTOM);    //设置图示样式
       lineChartView.getLegend().getLegendStyle().setShape(PointStyle.RECT);
        //设置图示比例
       lineChartView.getLegend().setLegendPercent(0.2f);
       
```

###### 7.标题

```
      //设置显示标题
        lineChartView.setShowChartName(true);
        //设置标题方向
        lineChartView.getChartTitle().setTitleDirection(IChartTitle.BOTTOM);
        //设置标题比例
        lineChartView.getChartTitle().setTitlePercent(0.2f);
        //设置标题样式
        lineChartView.getChartTitle().getTextStyle().setTextColor(res.getColor(R.color.arc21));
       
```

###### 8.数据设置

```
     //Y轴数据
     List<String> chartYDataList = new ArrayList<>();
      chartYDataList.add("华北");
                chartYDataList.add("华中");
                chartYDataList.add("华东");
                chartYDataList.add("华西");
                List<LineData> ColumnDatas = new ArrayList<>();
                ArrayList<Double> tempList1 = new ArrayList<>();
                tempList1.add(26d);
                tempList1.add(-35d);
                tempList1.add(-40d);
                tempList1.add(10d);
                LineData columnData1 = new LineData("温度","℃", IAxis.AxisDirection.RIGHT,res.getColor(R.color.arc3),tempList1);
                ArrayList<Double> humidityList = new ArrayList<>();
                humidityList.add(60d);
                humidityList.add(50d);
                humidityList.add(30d);
                humidityList.add(65d);
                LineData columnData2 = new LineData("湿度","RH%",res.getColor(R.color.arc2),humidityList);
                ColumnDatas.add(columnData1);
                ColumnDatas.add(columnData2);
                ChartData<LineData> chartData2 = new ChartData<>("线型图",chartYDataList,ColumnDatas);
        //设置数据
        lineChartView.setChartData(chartData);
```


######  9.动画

```
     //你可以使用默认动画 也可以设置Interpolator
     //startChartAnim(int duration, Interpolator interpolator)
      lineChartView.startChartAnim(400);
```
 
##### 更新日志

###### 1.4 版本

> 支持横向滚动，设置宽度比。
> 提供更多案例
> 后面将补充所有方法注释


###### 1.2版本更新日志

* 1.图表属性组件化，可定制化；
* 2.MarkView优化,解决自定义难问题；
* 3.支持Point Tip可定制化，Tip支持colorFilter,Alpha样式。

![动画](/img/gif/tip.gif)



###### 1.1版本更新日志

* 1.支持横轴文字旋转角度；
* 2.解决图表在列表以及ViewPager手势冲突；
* 3.竖轴支持设置StartZero以及最大,最小值。

###### 1.1版本功能
* 1.解决ViewPager以及列表手势冲突

![手势冲突](/img/gif/viewpager.gif)

* 2.横轴文字旋转

![旋转](/img/gif/rotate_axis_value.gif)


###### 1.0版本更新日志

* 1.增加了风向玫瑰图；
* 2.修复了Legend位置显示问题；
* 3.增加刻度的格式化。

###### 0.9版本更新日志

* 1.修复轴和网格显示不全问题；
* 2.增加雷达图旋转手势；
* 3.修复标题显示问题；
* 4.修复曲线和折线动态切换崩溃问题。

###### 0.8版本更新日志

* 1.新增散点图，面积图、饼图、3D柱状图；
* 2.支持饼图手势旋转和选中块；
* 3.修复之前0.7版本bug；
* 4.重构了代码，以便支持更多图表类型。

###### 0.7版本更新日志

* 1.支持线性图（折线，曲线，散点）柱状图，
* 2.支持图表轴方向，双轴，图示，水平线，十字轴，MarkView自定义，空白，标题，网格等
* 3.支持丰富的样式，包括字体样式（字体大小，颜色），图形样式（正方形，长方形，圆形），线（大小，颜色，DashPathEffect）
* 4.增加了图表移动和缩放功能以及动画。
        
    
##### 11.结尾
    
* 项目包括还有很多配置项，这里不一一列举，包括设置Padding等。
    做到几乎所有图表属性都可以配置，但由于这个框架刚写不久，还有很多待完善的地方。再一次感谢您花费时间阅读。
 
##### *License*

SmartChart is released under the Apache 2.0 license.

```
Copyright 2017 BakerJ.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at following link.

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitat


