(ns particles.particle
  (:require ))

(defn new-particle []
  "Creates a new particle record"
  (let [
    randomAngle (- (* (/ js/Math.PI 2.0) (.random js/Math)) (/ js/Math.PI 4.0))
    randomVelocity (+ 20 (* (.random js/Math) 60))
   ]

   {:x 0 
    :y 0 
    :age (int (rand 10))
    :dx (* (.sin js/Math randomAngle) randomVelocity) 
    :dy (- (* (.cos js/Math randomAngle) randomVelocity))}))

(defn is-alive? [particle]
  (and (< (:y particle) 768)
       (< (:age particle) 15)))

(defn step [particle]
  (-> particle
      (update :age inc)
      (update :x (partial + (:dx particle)))
      (update :y (partial + (:dy particle)))))

(defn color [particle]
  (let [ greenBlueValue (str (Math.round (* (/ (:age particle) 15.0) 255.0))) ]
    (str "rgb(255," greenBlueValue ",0)")
    ))