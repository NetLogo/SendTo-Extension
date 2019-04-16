package org.nlogo.extension.exportthe

import java.nio.file.{ Files, InvalidPathException, NoSuchFileException, Path, Paths }

import org.nlogo.api.{ Argument, Context, DefaultClassManager, ExtensionException, PrimitiveManager, Command }
import org.nlogo.core.Syntax

class SendToExtension extends DefaultClassManager {

  override def load(manager: PrimitiveManager): Unit = {
    manager.addPrimitive("file", FilePrim)
  }

  private object FilePrim extends Command {
    override def getSyntax = Syntax.commandSyntax(right = List(Syntax.StringType, Syntax.StringType, Syntax.OptionalType | Syntax.CommandType))
    override def perform(args: Array[Argument], context: Context): Unit = {
      val fileName = getPath(args(0).getString)
      val contents = args(1).getString
      val command  = args(2).getCommand
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
