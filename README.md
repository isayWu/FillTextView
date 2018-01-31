# FullTextView
TextView填满每行，实现排版整齐

## 效果图对比
![](https://raw.githubusercontent.com/isayWu/FullTextView/master/%E6%95%88%E6%9E%9C%E5%9B%BE.png)


## 使用方法：
1.  将FillTextView.java下载，并拷贝到自己的工程项， 然后就可以在布局文件中直接使用。

##### >>特别注意：
 * 仅重写了setText（String text)方法。所以只支持String类型的字符串，且只支持通过代码设置。
 * 只能在布局测量完成后, 才能通过setText(String text), 否则和原生TextView效果一样。

-------------------------------------------------
效果还是很好的。支持绝大多数应用场景。


## 实现思路：
1、通过添加“\n”来实现换行。重写了getText()方法，所以获取文字时获取的仍然是设置的文字。
2、通过一些逻辑尽量减少测量计算步骤

