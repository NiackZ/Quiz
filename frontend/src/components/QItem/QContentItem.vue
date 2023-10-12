<template>
  <v-row>
    <v-col cols="12" md="5" lg="4">
      <v-img src="../src/public/poster/darling.jpg"></v-img>
    </v-col>
    <v-col cols="12" md="7" lg="8" class="ff-verdana fs11pt">
      <div class="fs20pt">
        <span class="rus_name deep-purple">Любимый во Франксе</span> / <span class="en_name deep-purple">Darling in the FranXX</span>
      </div>

      <div class="mr2-0">
        <span>Тип:</span>
        <span class="span-tag">Сериал</span>
      </div>

      <div class="mr2-0">
        <span>Жанр:</span>
        <span class="span-tag">Романтика</span>
        <span class="span-tag">Этти</span>
      </div>


      <div class="mr2-0">
        <span>Студия:</span>
        <span class="span-tag">A-1 Pictures</span>
        <span class="span-tag">Studio Trigger</span>
      </div>

      <div class="mr2-0">
        <span>Сезон:</span>
        <span class="mrl5">17.08.2019 - 22.08.2019</span>
      </div>
      <div class="mr2-0">
        <span>Продолжительность:</span>
        <span class="mrl5">24 эп., по 25 мин</span>
      </div>

      <div class="mr2-0">
        <span>Ссылки:</span>
        <span class="mrl5">
          <a class="item-link" href="https://shikimori.one/animes/33352-violet-evergarden" target="_blank">Shiki</a>
        </span>
      </div>


      <div class="item_marks">
        <span>Метки:</span>
        <span class="span-tag">BDRip</span>
      </div>

      <div class="mr10-0 d-block text-justify">
        Далёкое будущее. Земля в руинах, и на остатки человечества наводят ужас таинственные гигантские формы жизни, известные как кёрю — «ревущие драконы». Чтобы противостоять им, люди создали передвижное фортификационное сооружение, названное Плантацией, где растут и обучаются юные пилоты роботов Франкс.<br><br>
        Единственная цель, ради которой живут эти дети, — уничтожение кёрю после прохождения специальной подготовки. Но чтобы управлять роботом — необходимо двое: мужчина и женщина, «тычинка» и «пестик». И если один не справляется, статуса пилота лишаются оба, что и для того, и для другого значит ни много ни мало утратить смысл существования.<br><br>
        Хиро считали одарённым. До тех пор, пока он не провалился на экзамене вместе со своей напарницей. Птица, которая никогда не взлетит, — так чувствовал себя мальчик с того времени и даже не догадывался, что скоро встретит человека с похожей судьбой...<br><br>
        «Убийца напарников», рогатая девчонка, в жилах которой течет кровь кёрю. «Станешь ли ты моим милым?» — спросила она не то шутки ради, не то всерьёз. И для Хиро вдруг снова нашлось место в мире...
      </div>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: 'QContentItem',
  props: {
    itemId: Number
  },
  mounted() {
    //document.title = `Element #${this.itemId}` // устанавливаем заголовок страницы
  }
}
</script>

<style scoped>
.v-img {
  border-radius: 3px;
}
.fs20pt {
  font-size: 20pt;
}
.fs11pt {
  font-size: 11pt;
}
.ff-verdana {
  font-family: Verdana;
}
.mr2-0{
  margin: 2px 0px;
}
.mrl5{
  margin-left: 5px;
}
.mr10-0{
  margin: 10px 0px;
}
.span-tag {
  margin-top: 5px;
  margin-left: 5px;
  cursor: pointer;
  display: inline-block;
  padding: 0px 5px;
  border-radius: 3px;
  background-color: rgba(25, 88, 255, 0.5);
  transition: 0.1s;
}
.span-tag:hover {
  color: gold;
}
.item-link{
  color: gold;
  text-decoration: none;
}
.item-link:hover{
  text-decoration: underline;
}
.item_description{

}
</style>