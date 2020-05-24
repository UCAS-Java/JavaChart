package com.ucas.listener;

import com.mysql.fabric.xmlrpc.base.ResponseParser;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;

public class TrafficStatisticsListener implements MenuListener {

    Container jPanel;
    public TrafficStatisticsListener(Container pane) {
        jPanel = pane;
    }

    @Override
    public void menuSelected(MenuEvent e) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setIgnoringElementContentWhitespace(true);
        DefaultCategoryDataset mDataset = new DefaultCategoryDataset();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(new ResponseParser(){
                @Override
                public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {
                    return new InputSource(getClass().getClassLoader().getResourceAsStream("AccessCount.dtd"));
                }
            });
            InputStream in = getClass().getClassLoader().getResourceAsStream("AccessCount.xml");
            Document doc = builder.parse(in);
            NodeList monthNodes = doc.getElementsByTagName("month");
            for (int i = 0; i < monthNodes.getLength(); ++i) {
                Element monthElement = (Element) monthNodes.item(i);
                mDataset.addValue(Double.valueOf(monthElement.getFirstChild().getTextContent()),
                        "2019年每月网站访问量折线图", monthElement.getAttribute("id"));
                //System.out.println(monthElement.getAttribute("id") + " " + monthElement.getFirstChild().getTextContent());
            }
            StandardChartTheme mChartTheme = new StandardChartTheme("CN");
            mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
            mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
            mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
            ChartFactory.setChartTheme(mChartTheme);
            JFreeChart mChart = ChartFactory.createLineChart(
                    "2019 traffic statistics",
                    "月份",
                    "次数",
                    mDataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );
            //ChartFrame mChartFrame = new ChartFrame("2019年网站访问量报表", mChart);
            JPanel  linePanel =  new JPanel(new BorderLayout());
            linePanel.setBounds(20, 20, 600, 600);
            ChartPanel chartPanel = new ChartPanel(mChart);
            linePanel.add(chartPanel, BorderLayout.CENTER);
            //mChartFrame.pack();
            //mChartFrame.setVisible(true);
            jPanel.add(linePanel);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}
