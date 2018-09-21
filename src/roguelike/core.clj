(ns roguelike.core
  (:gen-class))
(require '[clojure.string :as str])










(defstruct Block  :X :Y :Tiles)
(defstruct World  :Blocks :Entities)
(defstruct Entity :X :Y :Tile :Id)










(defn validTile?
  [tile]
  (contains? #{"." "#" "*"} tile))










(defn fillBlock
  "Creates a block, fills it with a specified tile. I should probably optimize this later."
  [x y tile]
  (let [realtile (if (validTile? tile) tile tile)
        row (take 8 (cycle [realtile]))
	      mat (take 8 (cycle [row     ]))]

        (struct Block (int x) (int y) mat)))










(defn showableworld
  "Filters blocks in based on whether or not they are viewable."
  [world x y rx ry]
  (let
    [lox (- x (/ rx 2))
     hix (+ x (/ rx 2))
     loy (- y (/ ry 2))
     hiy (+ y (/ ry 2)) ]
     (filter (fn [block]
                (let [bx (:X block)
                      by (:Y block)]
                     (and (< bx hix) (> bx lox)
                          (< by hiy) (> by loy))) (:Blocks world)))))










(defn tileAtCoord
  [world x y]
  (let [x_ (int (/ x 8))
        y_ (int (/ y 8))
        xi (mod (int x) 8)
        yi (mod (int y) 8)
        block (get (:Blocks world) [x_ y_])]
    (if (= nil block)
      " "
      (get (get (:Tiles block) yi) xi))))










(defn showWorld
  "Returns a string to draw the world."
  [world x y rx ry]
  (let
    [ents   (:Entities world)
     show   (showableworld world)
     rx_    (* 8 rx)
     ry_    (* 8 ry)
     row    (range (- x rx_) (+ x rx_))
     col    (range (- y ry_) (+ y ry_))
     coords (map (fn [i] (into [] (map #(vector i %) row))) col)
     tiles  (map #(map (fn [[a b]] (tileAtCoord world a b)) %) coords)]
  (apply str (map #(map (cons "\n" (fn [[a b]] (tileAtCoord world a b)) %)) coords))))










(defn showblock
  "Transforms a block into a string"
  [block]
  (str/join "\n" (map #(str/join "" %) (:Tiles block))))










(defn roomBlock
  [topdoor botdoor lftdoor rgtdoor x y]

  (let [coords (map (fn [i] (into [] (map #(vector i %) (range 0 8)))) (range 0 8))]
       (struct Block (int x) (int y)
         (map #(into [] (map (fn [[a b]]
                (cond
                  (and (= a 0) (= b topdoor)) "."
                  (and (= a 7) (= b botdoor)) "."
                  (and (= b 0) (= a lftdoor)) "."
                  (and (= b 7) (= a rgtdoor)) "."
                  (or  (= a 0) (= a 7) (= b 0) (= b 7)) "#"
                  :else ".")) %)) coords))))










(defn addBlockToWorld
  [world block]
  (assoc world block [(:X block) (:Y block)]))










(defn -main
  [& args]
  (println (showblock (roomBlock 8 8 3 8 0 0))))
