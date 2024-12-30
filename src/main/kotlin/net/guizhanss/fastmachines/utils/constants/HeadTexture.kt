package net.guizhanss.fastmachines.utils.constants

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils

enum class HeadTexture(
    val texture: String
) {

    // https://minecraft-heads.com/custom-heads/decoration/64403-technical-device
    MAIN("f1c9de1e420d65c21819e0a0c7ca5817c930052127b44a1334a246997f75e84f"),

    // https://minecraft-heads.com/custom-heads/alphabet/11214-quartz-arrow-up
    ARROW_UP("a99aaf2456a6122de8f6b62683f2bc2eed9abb81fd5bea1b4c23a58156b669"),

    // https://minecraft-heads.com/custom-heads/alphabet/11221-quartz-arrow-down
    ARROW_DOWN("3912d45b1c78cc22452723ee66ba2d15777cc288568d6c1b62a545b29c7187"),

    // https://minecraft-heads.com/custom-heads/alphabet/23223-information
    INFO("16439d2e306b225516aa9a6d007a7e75edd2d5015d113b42f44be62a517e574f"),

    ;

    fun getAsItem() = SlimefunUtils.getCustomHead(texture)
}
