//> using scala "3.2.1"
//> using lib "org.scalameta::mdoc:2.3.6"

import mdoc.MainSettings
import java.nio.file.Paths

val settings: MainSettings = mdoc
  .MainSettings()
  .withIn(Paths.get("./slides.md"))
  .withOut(Paths.get("./slides-scala.md"))

mdoc.Main.process(settings)
