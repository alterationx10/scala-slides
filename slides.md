---
title: Scala Slides
description: Generate Scala slides with mdoc, marp, and scala-cli
author: Mark Rudolph
keywords: scala,
url: https://alterationx10.com
marp: true
---

# Scala Slides

A slim pipeline to generate sweet, sweet Scala content with `scala-cli`, `mdoc`,
and `marp`

Repository: https://github.com/alterationx10/scala-slides

GitHub Pages: https://alterationx10.github.io/scala-slides

GitHub Template: https://github.com/alterationx10/scala-slides-template

---

# Fancy Example

Here is an example of some **Scala** code processed with `mdoc` to illustrate
how you can enhance your slides:

```scala mdoc
val addSarcasm: String => String =
  str =>
    str.zipWithIndex.map { s =>
      if (s._2 % 2 == 0)
        s._1.toUpper
      else
        s._1.toLower
    }.mkString

addSarcasm("Sarcasm is hard to convey on the internet.")
```

---

# Tools Used

1. mdoc - Typechecked markdown documentation for Scala
2. scala-cli - Command-line tool to interact with the Scala language
3. marp - The Markdown Presentation Ecosystem

---

# The Process

The overall process is straightforward:

1. We write our (`marp`) presentation in a markdown file, and annotate the scala
   code we want `mdoc` to process.
2. We run our `scala-cli` script, which uses `mdoc` to process the markdown
   file.
3. We run `marp` on the processed markdown file, and it generates our slides.

These tools can be installed/used in several configurations. This repo/example
uses a `package.json` to manage `marp`, and the build script. I've opted to use
`scala-cli` vs the `mdoc` cli directly, but once you are familiar with the tools
and their options, you can make set up things how you see fit.

---

# mdoc

To tell `mdoc` to evaluate something, you add _scala mdoc_ to the code fence.
For example, here is the the code fence from the previous slide un-processed:

````
```scala mdoc
val addSarcasm: String => String =
  str =>
    str.zipWithIndex.map { s =>
      if (s._2 % 2 == 0)
        s._1.toUpper
      else
        s._1.toLower
    }.mkString

addSarcasm("Sarcasm is hard to convey on the internet.")
```
````

https://scalameta.org/mdoc/

---

# slides.sc

The contents of our `scala-cli` script is not very fancy. We mainly set the
input/output files to be processed:

```scala
//> using scala "3.2.1"
//> using lib "org.scalameta::mdoc:2.3.6"

import mdoc.MainSettings
import java.nio.file.Paths

val settings: MainSettings = mdoc
  .MainSettings()
  .withIn(Paths.get("./slides.md"))
  .withOut(Paths.get("./slides-scala.md"))

mdoc.Main.process(settings)
```

---

# marp

> Marp (also known as the Markdown Presentation Ecosystem) provides an intuitive
> experience for creating beautiful slide decks. You only have to focus on
> writing your story in a Markdown document.

`marp` seems like a nice tool to make a slide deck without a lot of fanfare. You
can make a presentation in a single markdown file, titles start with a `#` and
pages are separated with `---`. See the next slide for an example based off of
the one from their site.

https://marp.app/

---

# marp layout example

```
---
theme: gaia
_class: lead
paginate: true
backgroundColor: #fff
---

# **Marp**

Markdown Presentation Ecosystem

https://marp.app/

---

# How to write slides

Split pages by horizontal ruler (`---`). It's very simple! :satisfied:

---

# Slide 1

foo

---

# Slide 2

bar
```

---

# GitHub Pages

Since we're using a `node` based project for `marp`, and `scala-cli`, there are
some convenient actions we can use to also deploy our slides right to GitHub
Pages via GitHub Actions.

Who doesn't love staring at some deployment YAML?

```yaml
steps:
  - uses: actions/checkout@v3
  - uses: coursier/cache-action@v6
  - uses: VirtusLab/scala-cli-setup@main
  - uses: actions/setup-node@v3
    with:
      node-version: 16
  - name: Build Slides
    run: yarn install && yarn build
```

Full File:
https://github.com/alterationx10/scala-slides/blob/main/.github/workflows/static.yaml
