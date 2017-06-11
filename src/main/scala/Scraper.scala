import java.io.FileOutputStream
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

import org.apache.poi.ss.util.{AreaReference, CellRangeAddress, CellReference}
import org.jsoup.Jsoup

import scala.collection.immutable.Range
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import scala.util.Try

object Scraper {
  def main(args: Array[String]): Unit = {
    val chooser = new JFileChooser
    val filter = new FileNameExtensionFilter("HTML Files", "htm", "html")
    chooser.setFileFilter(filter)
    val data = chooser.showOpenDialog(null) match {
      case JFileChooser.APPROVE_OPTION => {
        val doc = Jsoup.parse(chooser.getSelectedFile, "UTF-8", "")
        val entrants = doc.select("table.admin-table > tbody > tr[data-id]")
        for {
          index <- Range(0, entrants.size)
          entrant = entrants.get(index)
        } yield {
          val name = entrant.select("h3").text
          val source = entrant.select("h4").text
          val votes = entrant.select("td.admin-table__number")
          val actual = votes.first.text.toInt
          val weighted = Try(votes.next.first.text.toInt).getOrElse(0)
          (name, source, actual, weighted)
        }
      }.toList
      case _ => {}
        println("whelp")
        throw new Exception("It's not like I wanted you to pick a file or anything, b-baka!")
    }
    val wb = new XSSFWorkbook()
    val sheet = wb.createSheet()
    sheet.setAutoFilter(CellRangeAddress.valueOf("A1:D1"))//(new CellRangeAddress(0, data.size, 0, 3))
    val table = sheet.createTable()
    val cttable = table.getCTTable
    val table_style = cttable.addNewTableStyleInfo()
    table_style.setName("TableStyleMedium2")
    table_style.setShowColumnStripes(false)
    table_style.setShowRowStripes(true)
    table_style.setShowFirstColumn(false)
    table_style.setShowLastColumn(false)
    val data_range = new AreaReference(new CellReference(0, 0), new CellReference(data.size, 3))
    cttable.setRef(data_range.formatAsString())
    cttable.setDisplayName("MYTABLE")
    cttable.setName("Test")
    cttable.setId(1)
    val columns = cttable.addNewTableColumns()
    columns.setCount(4)
    val headers = List("Name", "Source", "Actual", "Weighted")
    for {
      index <- Range(0, 4)
    } yield {
      val column = columns.addNewTableColumn()
      column.setName(headers(index))
      column.setId(index+1)
    }
    for {
      r <- Range(0, data.size+1)
      row = sheet.createRow(r)
      c <- Range(0, 4)
    } yield {
      val cell = row.createCell(c)
      if (r == 0) {
        cell.setCellValue(headers(c))
      } else {
        c match {
          case 0 => cell.setCellValue(data(r-1)._1)
          case 1 => cell.setCellValue(data(r-1)._2)
          case 2 => cell.setCellValue(data(r-1)._3)
          case 3 => cell.setCellValue(data(r-1)._4)
        }
      }
    }
    val fileout = new FileOutputStream(chooser.getSelectedFile.getName + ".xlsx")
    wb.write(fileout)
    fileout.close
  }
}
