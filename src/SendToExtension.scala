package org.nlogo.extension.sendto

import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.{ Files, InvalidPathException, Path, Paths }

import org.nlogo.api.{ Argument, Context, DefaultClassManager, ExtensionException, PrimitiveManager, Command }
import org.nlogo.core.Syntax

class SendToExtension extends DefaultClassManager {

  override def load(manager: PrimitiveManager): Unit = {
    manager.addPrimitive("file", FilePrim)
  }

  private object FilePrim extends Command {
    override def getSyntax = Syntax.commandSyntax(right = List(Syntax.StringType, Syntax.StringType))
    override def perform(args: Array[Argument], context: Context): Unit = {
      val fileName = getPath(args(0).getString)
      val contents = args(1).getString
      try Files.write(fileName, contents.getBytes)
      catch {
        case ex: IOException =>
          throw new ExtensionException(s"Unable to write file: ${ex.getMessage}", ex)
      }
    }
  }

  private def getPath(path: String): Path = {
    try Paths.get(path)
    catch {
      case ex: InvalidPathException =>
        throw new ExtensionException(s"Could not parse path: ${ex.getMessage}", ex)
    }
  }
}
