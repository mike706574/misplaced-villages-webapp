(ns milo.client.subs
  (:require [re-frame.core :as rf]
            [milo.game :as game]
            [milo.menu :as menu]))

(rf/reg-sub
 :db
 (fn [db _]
   db))

(rf/reg-sub
 :screen
 (fn [db _]
   (:screen db)))

(rf/reg-sub
 :loading?
 (fn [db _]
   (:loading? db)))

(rf/reg-sub
 :status-message
 (fn [db _]
   (:status-message db)))

(rf/reg-sub
 :ready-games
 (fn [{:keys [:player :active-games]} _]
   (filter #(= (:milo.game/turn %) player) active-games)))

(rf/reg-sub
 :waiting-games
 (fn [{:keys [:player :active-games]} _]
   (filter #(not= (:milo.game/turn %) player) active-games)))

(rf/reg-sub
 :completed-games
 (fn [db _]
   (:completed-games db)))

(rf/reg-sub
 :received-invites
 (fn [db _]
   (:received-invites db)))

(rf/reg-sub
 :sent-invites
 (fn [db _]
   (:sent-invites db)))

(rf/reg-sub
 :messages
 (fn [db _]
   (:messages db)))

(rf/reg-sub
 :player
 (fn [db _]
   (:player db)))

(rf/reg-sub
 :error-message
 (fn [db _]
   (:error-message db)))


;; Move
(rf/reg-sub
 :card
 (fn [db _]
   (:card db)))

(rf/reg-sub
 :destination
 (fn [db _]
   (:destination db)))

(rf/reg-sub
 :source
 (fn [db _]
   (:source db)))

(rf/reg-sub
 :move-message
 (fn [db _]
   (:move-message db)))







;; Game
(defn game [db] (get-in db [:active-games (:game-id db)]))

(rf/reg-sub
 :game
 (fn [db _]
   (game db)))

(rf/reg-sub
 :opponent
 (fn [db _]
   (game/opponent (game db) (:player db))))

(rf/reg-sub
 :round-number
 (fn [db _]
   (inc (count (:milo.game/past-rounds (game db))))))

(rf/reg-sub
 :last-round
 (fn [db _]
   (last (:milo.game/past-rounds (game db)))))

(rf/reg-sub
 :hand
 (fn [{player :player :as db} _]
   (get-in (game db) [:milo.game/round
                      :milo.game/player-data
                      player
                      :milo.player/hand])))

(rf/reg-sub
 :expeditions
 (fn [db _]
   (let [game (game db)]
     (get-in game [:milo.game/round
                   :milo.game/player-data
                   (:player db)
                   :milo.player/expeditions]))))

(rf/reg-sub
 :opponent-expeditions
 (fn [db _]
   (let [game (game db)]
     (get-in game [:milo.game/round
                   :milo.game/player-data
                   (:milo.game/opponent game)
                   :milo.player/expeditions]))))

(rf/reg-sub
 :draw-count
 (fn [db _]
   (get-in (game db) [:milo.game/round :milo.game/draw-count])))

(rf/reg-sub
 :turn
 (fn [db _]
   (get-in (game db) [:milo.game/round :milo.game/turn])))

(rf/reg-sub
 :available-discards
 (fn [db _]
   (get-in (game db) [:milo.game/round :milo.game/available-discards])))

(rf/reg-sub
 :past-rounds
 (fn [db _]
   (:milo.game/past-rounds (game db))))