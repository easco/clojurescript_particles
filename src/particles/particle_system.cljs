(ns particles.particle-system
  (:require [particles.particle :as particle]))

(defn make-n-particles [n]
  (repeatedly n particle/new-particle))

(defn new-system []
  {:particles (make-n-particles 100)})

(defn apply-gravity [particle-list]
  (map (fn [p] (update p :dy (partial + 10))) particle-list))

(defn step-particles [particle-list]
  (map particle/step particle-list))

(defn remove-dead [particle-list]
  (filter particle/is-alive? particle-list))

(defn spawn-new [particle-list]
  (let [new-particles (make-n-particles (- 100 (count particle-list)))]
    (concat particle-list new-particles)))

(defn step [particle-system]
  (-> particle-system
      (update :particles step-particles)
      (update :particles remove-dead)
      (update :particles apply-gravity)
      (update :particles spawn-new)))
