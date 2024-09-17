package com.aj.proiecteim.tema3

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class RssFeed(
    @field:Element(name = "channel")
    var channel: Channel? = null
)

@Root(name = "channel", strict = false)
data class Channel(
    @field:ElementList(entry = "item", inline = true)
    var items: ArrayList<Item>? = null
)

@Root(name = "item", strict = false)
data class Item(
    @field:Element(name = "title")
    var title: String? = null,
    @field:Element(name = "link")
    var link: String? = null,
    @field:Element(name = "description")
    var description: String? = null,
    @field:Element(name = "pubDate")
    var pubDate: String? = null
)
