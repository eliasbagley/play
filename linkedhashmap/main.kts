
import java.util.*


class LinkedHashMap<K, V>() {

  data class Entry<K, V>(val key: K?, var value: V?, var before: Entry<K, V>? = null, var after: Entry<K, V>? = null)

  val m = 100
  val storage = Array<LinkedList<Entry<K, V>>>(m) { LinkedList<Entry<K, V>>() }

  var size = 0

  var dummy = Entry<K, V>(null, null)
  var head: Entry<K, V>? = dummy
  var tail: Entry<K, V>? = dummy

  fun size(): Int {
    return size
  }

  fun get(key: K): V? {
    return getEntry(key)?.value
  }

  private fun getEntry(key: K): Entry<K, V>? {
    val hc = key!!.hashCode()
    return storage[hc % m].filter { it.key == key }.firstOrNull()
  }

  fun put(key: K, value: V) {
    if (contains(key)) {
      getEntry(key)!!.value = value
    } else {
      val entry = Entry(key, value)
      val hc = key!!.hashCode()

      storage[hc % m].add(entry)
      size += 1

      tail?.after = entry
      entry.before = tail
      tail = entry
    }

  }

  fun remove(key: K): Boolean {
    if (!contains(key)) return false

    val hc = key!!.hashCode()
    val entry = getEntry(key)
    storage[hc % m].remove(entry)

    entry?.before?.after = entry?.after
    entry?.after?.before = entry?.before

    if (tail == entry) {
      tail = entry?.before
    }

    size -= 1

    return true
  }

  fun contains(key: K): Boolean {
    return getEntry(key) != null
  }

  fun keyset(): List<K> = keyset(head?.after)

  private fun keyset(head: Entry<K, V>?): List<K> {
    return if (head == null) {
      listOf()
    } else {
      listOf(head.key!!) + keyset(head.after)
    }
  }


}

val map = LinkedHashMap<Int, Int>()
println("Starting test suite")

println(map.get(1) ==  null)
println(map.size() == 0)

map.put(1, 1)
println(map.get(1) == 1)
println(map.size() == 1)

// keyset
map.put(2, 2)
println(map.get(2) == 2)
println(map.size() == 2)
println(map.keyset() == listOf(1, 2))

// remove
println("test removing")
println(map.remove(9) == false)
println(map.remove(2) == true)
println(map.get(2) == null)
println(map.size() == 1)
println(map.keyset() == listOf(1))

// add again
println("test adding key back in")
map.put(2, 2)
println(map.size() == 2)
println(map.keyset()  == listOf(1, 2))
//map.keyset().forEach { print("$it ") }
//println()
println(map.remove(1) == true)
println(map.get(1) == null)
println(map.keyset() == listOf(2))
println(map.size() == 1)
