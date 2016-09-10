# XiaoyaProgressBar
一个带进度信息的横向的progressbar和圆形的progressbar

## XiaoyaProgressBar 是一个以百分比显示进度的ProgressBar,支持横向ProgressBar和圆形ProgressBar，支持自定义TextColor，自定义ReachColor,ReachHeight,，自定义UnReachColor,UnReachHeight，效果如下：

![](https://github.com/sheng-xiaoya/XiaoyaProgressBar/blob/master/screen/progress_capture.gif)

##自定义属性：
### HorizontalProgressBarWithProgress支持的属性
| attr        | description           | format  |
| ------------- |:-------------:| -----:|
| progress_text_color     | 显示百分比的文字的颜色         | color |
| progress_text_size      | 文字的大小                     |   dimension |
| progress_text_offset    | 文字两边padding之和            |    dimension |
| progress_reach_color    | progress进度的颜色             |    color |
| progress_reach_height   | progress进度的height           |    dimension |
| progress_unreach_color  | 未到达progress进度的颜色        |    color |
| progress_unreach_height | 未到达progress进度的height      |    dimension |
### RoundProgressBarWithProgress支持的属性
因为RoundProgressBarWithProgress继承自HorizontalProgressBarWithProgress，所以HorizontalProgressBarWithProgress有的属性她都支持，同时加了一个progress_radius属性，代表圆的半径

| attr        | description           | format  |
| ------------- |:-------------:| -----:|
| progress_radius     | 圆的半径         | dimension |
