import java.io.{File, FileInputStream}

import org.apache.poi.xssf.usermodel.XSSFWorkbook

object Comparator {
  def main(args: Array[String]): Unit = {
    val doc1 = new FileInputStream(new File("AnimeBracket - Because sometimes a poll just isn't good enough.htm.xlsx"))
    val doc2 = new FileInputStream(new File("AnimeBracket - Because sometimes a poll just isn't good enough.htm_repaired.xlsx"))
    val book1 = new XSSFWorkbook(doc1)
    val book2 = new XSSFWorkbook(doc2)
    val sheet1 = book1.getSheetAt(0)
    val sheet2 = book2.getSheetAt(0)
    val table1 = sheet1.getTables.get(0)
    val table2 = sheet2.getTables.get(0)
    println("1\n" + table1)
    println("2\n" + table2)
    val cttable1 = table1.getCTTable
    val cttable2 = table2.getCTTable
    println("1\n" + cttable1)
    println("2\n" + cttable2)
    val style1 = cttable1.getTableStyleInfo
    val style2 = cttable2.getTableStyleInfo
    println("1\n" + style1)
    println("2\n" + style2)
    println("1\n" + sheet1.isAutoFilterLocked)
    println("2\n" + sheet2.isAutoFilterLocked)
  }
}
