package cn.darkfog.dialog_manager.msft

import java.io.Serializable

data class NLUResult(
    val domain: String,
    val intent: String,
    val slots: HashMap<String, String> = hashMapOf()
) : Serializable