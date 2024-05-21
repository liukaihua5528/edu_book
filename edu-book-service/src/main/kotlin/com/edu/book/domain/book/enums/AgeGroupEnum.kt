package com.edu.book.domain.book.enums

import org.apache.commons.lang3.ObjectUtils

/**
 * @Auther: liukaihua
 * @Date: 2024/3/27 20:23
 * @Description:
 */
enum class AgeGroupEnum(val age: Int, val desc: String) {

    ZERO_TO_TWO(0, "0-2"),

    THREE_TO_FOUR(1, "3-4"),

    FIVE_TO_SIX(2, "5-6"),

    SIX_TO_SEVEN(3, "6-7"),

    tkindergarten_to_school(4, "幼小衔接"),

    ;

    companion object {

        fun getDescByCode(age: Int): String {
            return AgeGroupEnum.values().toList().filter { ObjectUtils.equals(it.age, age) }.firstOrNull()?.desc ?: ""
        }

    }

}