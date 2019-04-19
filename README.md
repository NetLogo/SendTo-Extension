# Send-To

## What is it?

A NetLogo extension that lets you send string data outside of your NetLogo model.  Right now the only options is to send to a text file, using the `send-to:file` primitive.

## Primitives

| Prim Name | Arguments             | Behavior
| --------- | --------------------- | --------
| `file`    | *fileName* *contents* | Saves the *contents* string as the file *fileName*.  Depending on the platform, the user may get a chance to change the file name before it is saved.  If a file with the given name already exists, it may be overwritten.

## Building

Open it in SBT.  If you successfully run `package`, `send-to.jar` is created.

## Terms of Use

[![CC0](http://i.creativecommons.org/p/zero/1.0/88x31.png)](http://creativecommons.org/publicdomain/zero/1.0/)

The NetLogo Send-To extension is in the public domain.  To the extent possible under law, Uri Wilensky has waived all copyright and related or neighboring rights.
