(ns cljs-gravity.core)
; (:require [PIXI :as p]))

(enable-console-print!)

(println "Edits to this text should show up in your herp console.")

;; define your app data so that it doesn't get over-written on reload
(defonce app-state (atom {:text "Hello world!"}))

;; Procedural setup for PIXI
(def renderer (.autoDetectRenderer js/PIXI 800 600 {:backgroundColor 0x333333}))
(def stage (js/PIXI.Container.))
(def appElement (.getElementById js/document "app"))

;; Remove all children from app container
(while (.-firstChild appElement) (.removeChild appElement (.-firstChild appElement)))

(def state (atom 0))
(def pt (atom 0))

(def g (js/PIXI.Graphics.))
(.appendChild appElement (.-view renderer))
(.addChild stage g)
(.beginFill g 0xFFFFAA)
(.drawCircle g 50 50 10)
(.endFill g)
(.render renderer stage)

(defn animate [dt]
  (swap! state (fn [old] (if (> old 20) 0 (+ old (/ (- dt (deref pt)) 200)))))
  (swap! pt (fn [old] dt))
  (.clear g)
  (.beginFill g 0xFFFFAA)
  (.drawCircle g 50 50 (deref state))
  (.endFill g)
  (.render renderer stage)
  (.requestAnimationFrame js/window animate))
  

(.requestAnimationFrame js/window animate)

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
