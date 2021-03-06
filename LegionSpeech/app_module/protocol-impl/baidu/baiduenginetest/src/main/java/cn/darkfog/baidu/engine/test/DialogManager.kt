package cn.darkfog.baidu.engine.test

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import cn.darkfog.BaiduEngine
import cn.darkfog.foundation.arch.AppContextLinker
import cn.darkfog.foundation.log.CLog
import cn.darkfog.foundation.log.logD
import cn.darkfog.foundation.log.logE
import cn.darkfog.speech.protocol.stt.ASR
import cn.darkfog.speech.protocol.stt.AbstractSTTEngine
import cn.darkfog.speech.protocol.stt.EventType
import cn.darkfog.speech.protocol.stt.SpeechEvent
import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.*

object DialogManager : CLog {
    private val ttsEngine = TextToSpeech(AppContextLinker.context, { status ->
    }, "com.bmwgroup.apinext.china_speech_assistant")

    private val engine: AbstractSTTEngine = BaiduEngine
    var callback: Callback? = null

    val asrMap: HashMap<String, String> = hashMapOf(
        "" to ""
    )

    val responseMap: HashMap<String, String> = hashMapOf(
        "介绍" to "您好，我是暗雾研发的专属型智能管家，您可以称呼我为贾维斯。",
        "功能" to "先生，作为您的专属智能管家，我拥有管家的一切功能，包括但不限于住宅管理，资产管理，家庭成员保护与教育\n" +
                "帮助您处理日常事务，我还额外搭载了反诈骗，快速学习与检索等智能模块。",
        "管理住宅" to "先生，我拥有强大的运算能力与学习能力，基于您的个人习惯，我会时刻保持室内温度，湿度，灯光在您舒适的范围， " +
                "通过扫地机器人及其他清洁设备或者联系人工服务帮助您保持家庭整洁，各种传感器会让我第一时间察觉到出现在家里\n" +
                "以及附近的危险因素，及时处理电路异常，天然气异常等。",
        "个人习惯" to "先生，通过摄像头，红外，麦克风，我能从您的面部表情，语气，呼吸，体温等检测到您的心情，并记录您身体在发生\n" +
                "舒适信号时的各种指标。",
        "侵犯" to "先生，您不必担心，我拥有强大的运算能力，所有的您的相关数据都会在我这里被处理掉，他们甚至不会被上传网络，\n" +
                "从这点来说，我比所有的互联网公司甚至您的手机更安全，它们可能泄露您的数据，但我不会，甚至我还会主动检索\n" +

                "主人您身边的信息漏洞，搜索并删除您被泄漏的信息。",
        "不太好" to "先生，我支持完全离线运行，您可以尝试在离线情况下使用我。而且先生经过刚才的搜索，我发现您手机上的推销电\n" +
                "话过多，通过比对其他智能管家的数据，我们相信您在上次访问某某中介时候留下的信息被恶意泄露，我已经为您取证相\n" +
                "关数据并通知公安部门同时也清除了相关线上内容。同时暗网的扫描结果发现，您似乎是大规模数据泄露的受害者。\n" +
                "如果您允许我对您的日常数据进行监管，这种情况将不会再发生。",
        "家庭成员" to "先生，在征得您家人同意的情况下，我会实时记录整个家庭成员的当前位置，监控其生命体征，同时通过手机所处的\n" +
                "情况保证他们所处的环境安全。至于教育，先生我会观测您孩子的日常行为，记录使其感到愉悦或兴奋的行为，并通\n" +
                "过大数据推断出您孩子的天赋和兴趣所在，在通过天赋测试与意向测试以后，我会提供响应的在线教育功能，并且为\n" +
                "您的孩子建立适当的教育规划和终身档案，保证您孩子的天赋不被浪费。",
        "贵" to "先生，根据运算能力的不同，每款管家有不同售价，价格从6000 - 70000 不等，但是先生您不用担心，你可以与暗雾\n" +
                "合作的银行申请贷款，并加入Omni 计算平台。令人兴奋的是，以P4款管家为例，售价15000，但是按照当前计算资\n" +
                "源的价格来看，即使是我每天仅在您睡眠的8个小时里为平台提供计算资源，4年以后收益完全可以覆盖您的贷款，新\n" +
                "的计算设备，即使加上日常的各种在线服务支出，仍然有超过 80000 的收入。",
        "计算平台" to "Omni计算平台是暗雾研发的分布式计算网络，通过使用智能管家的闲置计算力，为各大公司，机构，实验室，提供廉价\n" +
                "稳定可靠的计算能力。先生，如果您同意我加入Omni计算平台，您不仅在获取收益，还在为全人类的进步做贡献。",
        "怎么说" to "先生，随着卫星技术的发展，研发和发射卫星的成本已经大大减少，随之而来的是更多的卫星数据，NASA在当今每天\n" +
                "需要处理超过24TB的数据，预计到2030年，仅全球气候变化产生的数据就会增加到 230 PB，而美国一年之内邮政服\n" +
                "务所发送的全部信件总数据量仅相当于 5 PB更不要提来自地球之外的数据，加入Omni ，每个人都在为太空探索做贡献。"
    )

    fun init(): Completable {
        return Completable.create {
            ttsEngine.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {

                }

                override fun onDone(utteranceId: String?) {
                    startDialog()
                }

                override fun onError(utteranceId: String?) {

                }

            })
            it.onComplete()
        }.andThen(engine.init())
    }

    fun startDialog() {
        engine.startRecognize()
            .subscribe(object : Observer<SpeechEvent> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: SpeechEvent) {
                    when (t.type) {
                        EventType.ASR_WUW, EventType.VAD_START, EventType.VAD_END -> Unit
                        EventType.ASR_PARTIAL -> {
                            val text = (t.data as ASR).text
                            callback?.onText(text, 0)
                        }
                        EventType.ASR_CLOUD -> {
                            val text = (t.data as ASR).text
                            callback?.onText(text, 0)
                            responseMap.keys.forEach {
                                if (text.contains(it)) {
                                    val response = responseMap[it]
                                    callback?.onText(response!!, 1)
                                    speak(response!!)
                                }
                            }
                        }
                    }
                    logD { "识别事件：$t" }
                }

                override fun onError(e: Throwable) {
                    logE(e) { "识别出错" }
                }

                override fun onComplete() {
                    logD { "识别结束" }
                }

            })

    }

    fun speak(text: String) {
        val uuid = UUID.randomUUID().toString()
        ttsEngine.speak(text, TextToSpeech.QUEUE_FLUSH, Bundle(), uuid)
    }
}

interface Callback {
    fun onText(text: String, tag: Int)
}