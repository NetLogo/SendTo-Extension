package org.nlogo.extension.sendto

import java.io.IOException
import java.nio.file.{ Files, InvalidPathException, Path, Paths }

import org.nlogo.api.{ Argument, Context, DefaultClassManager, ExtensionException, PrimitiveManager, Command, Workspace => ApiWorkspace }
import org.nlogo.core.Syntax
import org.nlogo.nvm.{ Workspace => NvmWorkspace }

class SendToExtension extends DefaultClassManager {

  override def load(manager: PrimitiveManager): Unit = {
    manager.addPrimitive("file", FilePrim)
  }

  private object FilePrim extends Command {
    override def getSyntax = Syntax.commandSyntax(right = List(Syntax.StringType, Syntax.StringType))
    override def perform(args: Array[Argument], context: Context): Unit = {
      val nw            = checkWorkspace(context.workspace)
      val userPath      = args(0).getString
      val workspacePath = nw.fileManager.attachPrefix(userPath)
      val filePath      = getPath(workspacePath)
      val contents      = args(1).getString
      try Files.write(filePath, contents.getBytes)
      catch {
        case ex: IOException =>
          throw new ExtensionException(s"Unable to write file: ${ex.getMessage}", ex)
      }
    }
  }

  private def checkWorkspace(workspace: ApiWorkspace): NvmWorkspace =
    workspace match {
      case nw: NvmWorkspace => nw
      case _                => throw new ExtensionException("We need an nvm.Workspace instance for access to the file manager.")
    }

  private def getPath(path: String): Path = {
    try Paths.get(path)
    catch {
      case ex: InvalidPathException =>
        throw new ExtensionException(s"Could not parse path: ${ex.getMessage}", ex)
    }
  }
}
