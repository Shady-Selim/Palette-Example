package com.example.myapplication

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val result = ArrayList<String>()

    @Test
    fun test() {
        val a = listOf<String>("a", "b", "c", "d", "e", "f")
        val b = listOf<String>("1", "2", "3")
        val c = 3
        mergeTwoLists(a, b, c)
        //println(result.toString())
    }

    private fun mergeTwoLists(listA: List<String>, listB: List<String>, c: Int) {

        for (index in 0..listA.size + listB.size) {
            val interval = c + (c * index)
            for (aIndex in index * c until interval) {
                if (index * c < listA.size)
                    println(listA[aIndex])
            }
            if (index < listB.size)
                println(listB[index])
        }
    }

    @Test
    fun test2(){
        var arr1 = arrayListOf<String>("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k")
        var arr2 = arrayListOf<String>("1", "2", "3")
        var c = 2
        var resultArr = arrayListOf<String>().apply { addAll(arr1) }

        fun concatArrays() {

            for (i in c until arr1.size+1 step c) {
                var arr2CurrentIndex = i/c -1
                if (arr2CurrentIndex < arr2.size)
                    resultArr.add(i+arr2CurrentIndex, arr2[arr2CurrentIndex])
                else
                    break
            }
            println(resultArr.toString())
        }
        concatArrays()
    }

    @Test
    fun test3(){
        fun buildNewArray(A: ArrayList<String>, B: ArrayList<String>, c: Int): ArrayList<String> {
            var D = ArrayList<String>()
            var j = 0
            for (i in 0 until A.size) {
                D.add(A[i])
                if ((i + 1) % c == 0 && j < B.size) {
                    D.add(B[j++])
                }
            }
            return D
        }
        val A = arrayListOf("A", "B", "C", "D", "E", "F")
        val B = arrayListOf("1", "2", "3")
        val c = 1
        println(buildNewArray(A, B, c))
    }

    @Test
    fun test4(){
        val count = 4
        var arrayA: ArrayList<String> = ArrayList()
        arrayA.add("A")
        arrayA.add("B")
        arrayA.add("C")
        arrayA.add("D")
        arrayA.add("E")
        arrayA.add("F")
        var arrayB: ArrayList<String> = ArrayList()
        arrayB.add("1")
        arrayB.add("2")
        arrayB.add("3")

        fun compineArray(
                count: Int,
                arrayA: ArrayList<String>,
                arrayB: ArrayList<String>
        ): ArrayList<String> {
            var bCount = 0
            var aCount = 0

            var addFromArrayB = false

            var totalNum = arrayA.size / count
            totalNum = totalNum + arrayA.size - 1

            var arrayZ: ArrayList<String> = ArrayList()

            for (item in 0..totalNum) {

                if (item != 0 && addFromArrayB) {
                    addFromArrayB = false
                    if (bCount < arrayB.size) {
                        arrayZ.add(arrayB.get(bCount))
                        bCount += 1
                    }
                } else {
                    arrayZ.add(arrayA.get(aCount))
                    aCount += 1

                    if (aCount % count == 0)
                        addFromArrayB = true
                    else
                        addFromArrayB = false

                }

            }

            return arrayZ

        }

        println("task "+ compineArray(count, arrayA, arrayB).toString())

    }

    @Test
    fun solve() {
        val a = listOf('a', 'b', 'c', 'd', 'e', 'f')
        val b = listOf('1', '2', '3', '4')
        val c = 1
        //  - a = [‘a’, ‘b’, ‘c’, ‘d’, ‘e’, ‘f’]
//  - b = [‘1’, ‘2’, ‘3’]
//  - c = 2
//  - d = [‘a’, ‘b’, ’1’, ‘c’, ‘d’, ’2’, ‘e’, ‘f’, ’3’]


        val d = mutableListOf<Char>()
        var counterOverA = 0
        if (c != 0) {
            b.forEach { char ->
                if (counterOverA + c < a.size) {
                    d.addAll(a.subList(counterOverA, counterOverA + c))
                    d.add(char)
                    counterOverA += c
                }
            }
            d.addAll(a.subList(counterOverA, a.size))

        } else {
            d.addAll(a)
            d.addAll(b)
        }


        //prlin line
        d.forEach {
            println(it)
        }
    }
}

