(ns particles.core
  (:require-macros [hiccups.core :as hiccups :refer [html]])
  (:require [hiccups.runtime :as hiccupsrt]
            [particles.particle-system :as particle-system]
            [particles.particle :as particle]
            [rxjs :as rxjs]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom (particle-system/new-system)))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )

(defn particle-to-circle [particle]
  [:circle {:cx (str (:x particle))
            :cy (str (:y particle))
            :r (str (- 15 (:age particle)))
            :fill (particle/color particle)}])

(defn particles-to-circles [particle-list]
  (map particle-to-circle particle-list))

(hiccups/defhtml particle-image [particle-system]
  [:svg {:xmlns "http://www.w3.org/2000/svg" :width "1024" :height "768" :viewBox "0 0 1024 768"}
   [:rect {:fill "black" :x "0" :y "0" :width "1024" :height "640"}]
   [:g {:transform "translate(512,384)"}
    (particles-to-circles (:particles particle-system))]])

(defn tick [_]
  (swap! app-state particle-system/step)
  (let [image (.getElementById js/document "particle_image")]
    (if image (set! image -innerHTML (particle-image @app-state)))))

(def image (.getElementById js/document "particle_image"))
(def rxjs-stack (-> (rxjs/timer (/ 1000 12) (/ 1000 12))
                    (. subscribe tick)))